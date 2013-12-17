using System;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Web.Services.Protocols;
using System.Xml.Linq;
using System.Web.Script.Services;
using System.Drawing;
using System.IO;

[WebService(Namespace = "http://tempuri.org/")]
[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
// [System.Web.Script.Services.ScriptService]
public class Service : System.Web.Services.WebService
{
 
    myelibDataContext mylib = new myelibDataContext();
    public Service () {

        //Uncomment the following line if using designed components 
        //InitializeComponent(); 
    }
    /*Login through enrollment number and library card number*/            
    [WebMethod]
    public string Login(string stud_enroll_no,string stud_libcard_no)
    {
        returnType.Login obj = new returnType.Login();
        var logindetails = from tp in mylib.stud_masts where tp.stud_enroll_no==stud_enroll_no & tp.stud_libcard_no==stud_libcard_no  select tp;
        try
        {
            if (logindetails.Count() > 0)
            {

                obj.stud_enroll_no = string.IsNullOrEmpty(logindetails.SingleOrDefault().stud_enroll_no.ToString()) ? "" : logindetails.SingleOrDefault().stud_enroll_no.ToString();
                obj.stud_libcard_no = string.IsNullOrEmpty(logindetails.SingleOrDefault().stud_libcard_no.ToString()) ? "" : logindetails.SingleOrDefault().stud_libcard_no.ToString();
                obj.stud_fname = string.IsNullOrEmpty(logindetails.SingleOrDefault().stud_fname.ToString()) ? "" : logindetails.SingleOrDefault().stud_fname.ToString();
                obj.stud_mname = string.IsNullOrEmpty(logindetails.SingleOrDefault().stud_mname.ToString()) ? "" : logindetails.SingleOrDefault().stud_mname.ToString();
                obj.stud_lname = string.IsNullOrEmpty(logindetails.SingleOrDefault().stud_lname.ToString()) ? "" : logindetails.SingleOrDefault().stud_lname.ToString();
                obj.stud_class = string.IsNullOrEmpty(logindetails.SingleOrDefault().stud_class.ToString()) ? "" : logindetails.SingleOrDefault().stud_class.ToString();
                obj.stud_dept = string.IsNullOrEmpty(logindetails.SingleOrDefault().stud_dept.ToString()) ? "" : logindetails.SingleOrDefault().stud_dept.ToString();
                obj.stud_email = string.IsNullOrEmpty(logindetails.SingleOrDefault().stud_email.ToString()) ? "" : logindetails.SingleOrDefault().stud_email.ToString();
                obj.stud_mob_no = logindetails.SingleOrDefault().stud_mob_no;
                obj.ResponseCode = "1";
                obj.ResponseMsg = "Successful Transaction";
            }
            else
            {
                obj.ResponseCode = "3";
                obj.ResponseMsg = "Username or password is wrong please correct it";
            }
         
        }
        catch (Exception ex)
        {
            obj.ResponseCode = "2";
            obj.ResponseMsg = "Error on server side : " + ex.Message.ToString();

        }
        System.Web.Script.Serialization.JavaScriptSerializer SObj = new System.Web.Script.Serialization.JavaScriptSerializer();
        String JsonStr = SObj.Serialize(obj); 
        Context.Response.ContentType = "application/json";
        Context.Response.AddHeader("content-length", JsonStr.Length.ToString());
        Context.Response.Write(JsonStr);
        Context.Response.End();
        return "";
            
    }
	/*Login through barcode scnning*/
    [WebMethod]
    [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
    public string Login_barcode(string stud_libcard_no)
    {
        returnType.Login_Barcode obj = new returnType.Login_Barcode();


        var logindetails = from tp in mylib.stud_masts where tp.stud_libcard_no == stud_libcard_no select tp;
        try
        {

            if (logindetails.Count() > 0)
            {

                obj.stud_enroll_no = string.IsNullOrEmpty(logindetails.SingleOrDefault().stud_enroll_no.ToString()) ? "" : logindetails.SingleOrDefault().stud_enroll_no.ToString();
                obj.stud_libcard_no = string.IsNullOrEmpty(logindetails.SingleOrDefault().stud_libcard_no.ToString()) ? "" : logindetails.SingleOrDefault().stud_libcard_no.ToString();
                obj.stud_fname = string.IsNullOrEmpty(logindetails.SingleOrDefault().stud_fname.ToString()) ? "" : logindetails.SingleOrDefault().stud_fname.ToString();
                obj.stud_mname = string.IsNullOrEmpty(logindetails.SingleOrDefault().stud_mname.ToString()) ? "" : logindetails.SingleOrDefault().stud_mname.ToString();
                obj.stud_lname = string.IsNullOrEmpty(logindetails.SingleOrDefault().stud_lname.ToString()) ? "" : logindetails.SingleOrDefault().stud_lname.ToString();
                obj.stud_class = string.IsNullOrEmpty(logindetails.SingleOrDefault().stud_class.ToString()) ? "" : logindetails.SingleOrDefault().stud_class.ToString();
                obj.stud_dept = string.IsNullOrEmpty(logindetails.SingleOrDefault().stud_dept.ToString()) ? "" : logindetails.SingleOrDefault().stud_dept.ToString();
                obj.stud_email = string.IsNullOrEmpty(logindetails.SingleOrDefault().stud_email.ToString()) ? "" : logindetails.SingleOrDefault().stud_email.ToString();
                obj.stud_mob_no = logindetails.SingleOrDefault().stud_mob_no;
                obj.ResponseCode = "1";
                obj.ResponseMsg = "Successful Transaction";
            }
            else
            {
                obj.ResponseCode = "3";
                obj.ResponseMsg = "Your librarycard number is wrong please correct it";

            }

        }
        catch (Exception ex)
        {
            obj.ResponseCode = "2";
            obj.ResponseMsg = "Error on server side : " + ex.Message.ToString();

        }
        System.Web.Script.Serialization.JavaScriptSerializer SObj = new System.Web.Script.Serialization.JavaScriptSerializer();
        String JsonStr = SObj.Serialize(obj);
        Context.Response.ContentType = "application/json";
        Context.Response.AddHeader("content-length", JsonStr.Length.ToString());
        Context.Response.Write(JsonStr);
        Context.Response.End();
        return "";

    }
	/*Search book by keyword*/
    [WebMethod]
    public string BkDetail_Keyword(string bk_keyword)
    {
        returnType.BkDetail_Keyword obj = new returnType.BkDetail_Keyword();
        var bkdetails = from tp in mylib.book_masts where tp.bk_id.ToString().Contains(bk_keyword) || tp.bk_name.ToString().Contains(bk_keyword) || tp.bk_auther.ToString().Contains(bk_keyword) || tp.bk_publisher.ToString().Contains(bk_keyword) select tp;
        //var likeeg = from tp in mylib.book_masts where tp.bk_id.ToString().Contains(bk_keyword) || tp.bk_name.ToString().Contains(bk_keyword) || tp.bk_auther.ToString().Contains(bk_keyword) || tp.bk_publisher.ToString().Contains(bk_keyword) select tp;
        
        var bdk = bkdetails.ToList();
        try
        {
            if (bkdetails.Count() > 0)
            {
                obj.BkDetail = new returnType.Bk_Detail_Keyword[bkdetails.Count()];

                for (int i = 0; i < bkdetails.Count(); i++)
                {
                    obj.BkDetail[i] = new returnType.Bk_Detail_Keyword();
                    obj.BkDetail[i].Dbk_id = string.IsNullOrEmpty(bdk[i].bk_id.ToString()) ? "" : bdk[i].bk_id.ToString();
                    obj.BkDetail[i].Dbk_name = string.IsNullOrEmpty(bdk[i].bk_name.ToString()) ? "" : bdk[i].bk_name.ToString();
                    obj.BkDetail[i].Dbk_auther = string.IsNullOrEmpty(bdk[i].bk_auther.ToString()) ? "" : bdk[i].bk_auther.ToString();
                    obj.BkDetail[i].Dbk_publisher = string.IsNullOrEmpty(bdk[i].bk_publisher.ToString()) ? "" : bdk[i].bk_publisher.ToString();
                    obj.BkDetail[i].Dbk_desc = string.IsNullOrEmpty(bdk[i].bk_desc.ToString()) ? "" : bdk[i].bk_desc.ToString();
                    obj.BkDetail[i].Dbk_stock = bdk[i].bk_stock.ToString();
                    obj.BkDetail[i].Dbk_stock = bdk[i].bk_stock.ToString();
                    obj.BkDetail[i].Dbk_stock = bdk[i].bk_stock.ToString();
                    obj.BkDetail[i].Dbk_avail = bdk[i].bk_avail.ToString();
                    obj.ResponseCode = "1";
                    obj.ResponseMsg = "Successful Transaction";

                }
            }
            else
            {
                obj.ResponseCode = "3";
                obj.ResponseMsg = "No book of this keyword is available";

            }
        }
        catch (Exception ex)
        {
            obj.ResponseCode = "2";
            obj.ResponseMsg = "Error on server side : " + ex.Message.ToString();

        }
        System.Web.Script.Serialization.JavaScriptSerializer SObj = new System.Web.Script.Serialization.JavaScriptSerializer();
        String JsonStr = SObj.Serialize(obj);
        Context.Response.ContentType = "application/json";
        Context.Response.AddHeader("content-length", JsonStr.Length.ToString());
        Context.Response.Write(JsonStr);
        Context.Response.End();
        return "";

    }
	/*Search book by id*/
    [WebMethod]
    public string BkDetail_ID(string bk_id)
    {
        returnType.BkDetail_ID obj = new returnType.BkDetail_ID();
        var bkdetails = from tp in mylib.book_masts where tp.bk_id.ToString() == bk_id select tp;
        var bd = bkdetails.ToList();
        try
        {
            if (bkdetails.Count() > 0)
            {
                obj.BkDetail = new returnType.Bk_Detail[bkdetails.Count()];

                for (int i = 0; i < bkdetails.Count(); i++)
                {
                    obj.BkDetail[i] = new returnType.Bk_Detail();
                    obj.BkDetail[i].Dbk_id = string.IsNullOrEmpty(bd[i].bk_id.ToString()) ? "" : bd[i].bk_id.ToString();
                    obj.BkDetail[i].Dbk_name = string.IsNullOrEmpty(bd[i].bk_name.ToString()) ? "" : bd[i].bk_name.ToString();
                    obj.BkDetail[i].Dbk_auther = string.IsNullOrEmpty(bd[i].bk_auther.ToString()) ? "" : bd[i].bk_auther.ToString();
                    obj.BkDetail[i].Dbk_publisher = string.IsNullOrEmpty(bd[i].bk_publisher.ToString()) ? "" : bd[i].bk_publisher.ToString();
                    obj.BkDetail[i].Dbk_desc = string.IsNullOrEmpty(bd[i].bk_desc.ToString()) ? "" : bd[i].bk_desc.ToString();
                    obj.BkDetail[i].Dbk_stock = bd[i].bk_stock.ToString();
                    obj.BkDetail[i].Dbk_stock = bd[i].bk_stock.ToString();
                    obj.BkDetail[i].Dbk_stock = bd[i].bk_stock.ToString();
                    obj.BkDetail[i].Dbk_avail = bd[i].bk_avail.ToString();
                    obj.ResponseCode = "1";
                    obj.ResponseMsg = "Successful Transaction";

                }
            }
            else
            {
                obj.ResponseCode = "3";
                obj.ResponseMsg = "No book of this id is available";

            }
        }
        catch (Exception ex)
        {
            obj.ResponseCode = "2";
            obj.ResponseMsg = "Error on server side : " + ex.Message.ToString();

        }
        System.Web.Script.Serialization.JavaScriptSerializer SObj = new System.Web.Script.Serialization.JavaScriptSerializer();
        String JsonStr = SObj.Serialize(obj);
        Context.Response.ContentType = "application/json";
        Context.Response.AddHeader("content-length", JsonStr.Length.ToString());
        Context.Response.Write(JsonStr);
        Context.Response.End();
        return "";

    }
	/*Search book by name*/
    [WebMethod]
    public string BkDetail_Name(string bk_name)
    {
        returnType.BkDetail_Name obj = new returnType.BkDetail_Name();
        var bkdetails = from tp in mylib.book_masts where tp.bk_name.ToString() == bk_name select tp;
        var bdn = bkdetails.ToList();
        try
        {
            if (bkdetails.Count() > 0)
            {
                obj.BkDetailN = new returnType.Bk_Detail_Name[bkdetails.Count()];

                for (int i = 0; i < bkdetails.Count(); i++)
                {
                    obj.BkDetailN[i] = new returnType.Bk_Detail_Name();
                    obj.BkDetailN[i].Dbk_id = string.IsNullOrEmpty(bdn[i].bk_id.ToString()) ? "" : bdn[i].bk_id.ToString();
                    obj.BkDetailN[i].Dbk_name = string.IsNullOrEmpty(bdn[i].bk_name.ToString()) ? "" : bdn[i].bk_name.ToString();
                    obj.BkDetailN[i].Dbk_auther = string.IsNullOrEmpty(bdn[i].bk_auther.ToString()) ? "" : bdn[i].bk_auther.ToString();
                    obj.BkDetailN[i].Dbk_publisher = string.IsNullOrEmpty(bdn[i].bk_publisher.ToString()) ? "" : bdn[i].bk_publisher.ToString();
                    obj.BkDetailN[i].Dbk_desc = string.IsNullOrEmpty(bdn[i].bk_desc.ToString()) ? "" : bdn[i].bk_desc.ToString();
                    obj.BkDetailN[i].Dbk_stock = bdn[i].bk_stock.ToString();
                    obj.BkDetailN[i].Dbk_stock = bdn[i].bk_stock.ToString();
                    obj.BkDetailN[i].Dbk_stock = bdn[i].bk_stock.ToString();
                    obj.BkDetailN[i].Dbk_avail = bdn[i].bk_avail.ToString();
                    obj.ResponseCode = "1";
                    obj.ResponseMsg = "Successful Transaction";

                }
            }
            else
            {
                obj.ResponseCode = "3";
                obj.ResponseMsg = "No book of this name is available";

            }
        }
        catch (Exception ex)
        {
            obj.ResponseCode = "2";
            obj.ResponseMsg = "Error on server side : " + ex.Message.ToString();

        }
        System.Web.Script.Serialization.JavaScriptSerializer SObj = new System.Web.Script.Serialization.JavaScriptSerializer();
        String JsonStr = SObj.Serialize(obj);
        Context.Response.ContentType = "application/json";
        Context.Response.AddHeader("content-length", JsonStr.Length.ToString());
        Context.Response.Write(JsonStr);
        Context.Response.End();
        return "";

    }

	/*Search book by auther name*/
    [WebMethod]
    public string BkDetail_Auther(string bk_auther)
    {
        returnType.BkDetail_Auther obj = new returnType.BkDetail_Auther();
        var bkdetails = from tp in mylib.book_masts where tp.bk_auther.ToString() == bk_auther select tp;
        var bda = bkdetails.ToList();
        try
        {
            if (bkdetails.Count() > 0)
            {
                obj.BkDetailA = new returnType.Bk_Detail_Auth[bkdetails.Count()];

                for (int i = 0; i < bkdetails.Count(); i++)
                {
                    obj.BkDetailA[i] = new returnType.Bk_Detail_Auth();
                    obj.BkDetailA[i].Dbk_id = string.IsNullOrEmpty(bda[i].bk_id.ToString()) ? "" : bda[i].bk_id.ToString();
                    obj.BkDetailA[i].Dbk_name = string.IsNullOrEmpty(bda[i].bk_name.ToString()) ? "" : bda[i].bk_name.ToString();
                    obj.BkDetailA[i].Dbk_auther = string.IsNullOrEmpty(bda[i].bk_auther.ToString()) ? "" :bda[i].bk_auther.ToString();
                    obj.BkDetailA[i].Dbk_publisher = string.IsNullOrEmpty(bda[i].bk_publisher.ToString()) ? "" : bda[i].bk_publisher.ToString();
                    obj.BkDetailA[i].Dbk_desc = string.IsNullOrEmpty(bda[i].bk_desc.ToString()) ? "" : bda[i].bk_desc.ToString();
                    obj.BkDetailA[i].Dbk_stock = bda[i].bk_stock.ToString();
                    obj.BkDetailA[i].Dbk_stock = bda[i].bk_stock.ToString();
                    obj.BkDetailA[i].Dbk_stock = bda[i].bk_stock.ToString();
                    obj.BkDetailA[i].Dbk_avail = bda[i].bk_avail.ToString();
                    obj.ResponseCode = "1";
                    obj.ResponseMsg = "Successful Transaction";

                }
            }
            else
            {
                obj.ResponseCode = "3";
                obj.ResponseMsg = "No book of this auther is available";

            }
        }
        catch (Exception ex)
        {
            obj.ResponseCode = "2";
            obj.ResponseMsg = "Error on server side : " + ex.Message.ToString();

        }
        System.Web.Script.Serialization.JavaScriptSerializer SObj = new System.Web.Script.Serialization.JavaScriptSerializer();
        String JsonStr = SObj.Serialize(obj);
        Context.Response.ContentType = "application/json";
        Context.Response.AddHeader("content-length", JsonStr.Length.ToString());
        Context.Response.Write(JsonStr);
        Context.Response.End();
        return "";

    }
	/*Search book by publisher name*/
    [WebMethod]
    public string BkDetail_Publisher(string bk_publisher)
    {
        returnType.BkDetail_Publisher obj = new returnType.BkDetail_Publisher();
        var bkdetails = from tp in mylib.book_masts where tp.bk_publisher.ToString() == bk_publisher select tp;
        var bdp = bkdetails.ToList();
        try
        {
            if (bkdetails.Count() > 0)
            {
                obj.BkDetailP = new returnType.Bk_Detail_Publisher[bkdetails.Count()];

                for (int i = 0; i < bkdetails.Count(); i++)
                {
                    obj.BkDetailP[i] = new returnType.Bk_Detail_Publisher();
                    obj.BkDetailP[i].Dbk_id = string.IsNullOrEmpty(bdp[i].bk_id.ToString()) ? "" : bdp[i].bk_id.ToString();
                    obj.BkDetailP[i].Dbk_name = string.IsNullOrEmpty(bdp[i].bk_name.ToString()) ? "" : bdp[i].bk_name.ToString();
                    obj.BkDetailP[i].Dbk_auther = string.IsNullOrEmpty(bdp[i].bk_auther.ToString()) ? "" : bdp[i].bk_auther.ToString();
                    obj.BkDetailP[i].Dbk_publisher = string.IsNullOrEmpty(bdp[i].bk_publisher.ToString()) ? "" : bdp[i].bk_publisher.ToString();
                    obj.BkDetailP[i].Dbk_desc = string.IsNullOrEmpty(bdp[i].bk_desc.ToString()) ? "" : bdp[i].bk_desc.ToString();
                    obj.BkDetailP[i].Dbk_stock = bdp[i].bk_stock.ToString();
                    obj.BkDetailP[i].Dbk_stock = bdp[i].bk_stock.ToString();
                    obj.BkDetailP[i].Dbk_stock = bdp[i].bk_stock.ToString();
                    obj.BkDetailP[i].Dbk_avail = bdp[i].bk_avail.ToString();
                    obj.ResponseCode = "1";
                    obj.ResponseMsg = "Successful Transaction";

                }
            }
            else
            {
                obj.ResponseCode = "3";
                obj.ResponseMsg = "No book of this publisher is available";

            }
        }
        catch (Exception ex)
        {
            obj.ResponseCode = "2";
            obj.ResponseMsg = "Error on server side : " + ex.Message.ToString();

        }
        System.Web.Script.Serialization.JavaScriptSerializer SObj = new System.Web.Script.Serialization.JavaScriptSerializer();
        String JsonStr = SObj.Serialize(obj);
        Context.Response.ContentType = "application/json";
        Context.Response.AddHeader("content-length", JsonStr.Length.ToString());
        Context.Response.Write(JsonStr);
        Context.Response.End();
        return "";

    }
    
    
	/*Student detail*/
    [WebMethod]
    public string Stud_Detail(string stud_libcard_no)
    {
        returnType.Stud_Detail obj = new returnType.Stud_Detail();
        var studdetails = from tp in mylib.stud_masts where tp.stud_libcard_no.ToString()==stud_libcard_no select tp;
        try
        {
            if (studdetails.Count() > 0)
            {
                obj.stud_enroll_no = string.IsNullOrEmpty(studdetails.SingleOrDefault().stud_enroll_no.ToString()) ? "" : studdetails.SingleOrDefault().stud_enroll_no.ToString();
                obj.stud_fname = string.IsNullOrEmpty(studdetails.SingleOrDefault().stud_fname.ToString()) ? "" : studdetails.SingleOrDefault().stud_fname.ToString();
                obj.stud_mname = string.IsNullOrEmpty(studdetails.SingleOrDefault().stud_mname.ToString()) ? "" : studdetails.SingleOrDefault().stud_mname.ToString();
                obj.stud_lname = string.IsNullOrEmpty(studdetails.SingleOrDefault().stud_lname.ToString()) ? "" : studdetails.SingleOrDefault().stud_lname.ToString();
                obj.stud_class = string.IsNullOrEmpty(studdetails.SingleOrDefault().stud_class.ToString()) ? "" : studdetails.SingleOrDefault().stud_class.ToString();
                obj.stud_dept = string.IsNullOrEmpty(studdetails.SingleOrDefault().stud_dept.ToString()) ? "" : studdetails.SingleOrDefault().stud_dept.ToString();
                obj.stud_libcard_no = string.IsNullOrEmpty(studdetails.SingleOrDefault().stud_libcard_no.ToString()) ? "" : studdetails.SingleOrDefault().stud_libcard_no.ToString();
                obj.stud_email = string.IsNullOrEmpty(studdetails.SingleOrDefault().stud_email.ToString()) ? "" : studdetails.SingleOrDefault().stud_email.ToString();
                obj.stud_mob_no = studdetails.SingleOrDefault().stud_mob_no;
                obj.ResponseCode = "1";
                obj.ResponseMsg = "Successful Transaction";
            }
            else
            {
                obj.ResponseCode = "3";
                obj.ResponseMsg = "No record found";
            }
        }
        catch (Exception ex)
        {
            obj.ResponseCode = "2";
            obj.ResponseMsg = "Error on server side : " + ex.Message.ToString();

        }
        System.Web.Script.Serialization.JavaScriptSerializer SObj = new System.Web.Script.Serialization.JavaScriptSerializer();
        String JsonStr = SObj.Serialize(obj);
        Context.Response.ContentType = "application/json";
        Context.Response.AddHeader("content-length", JsonStr.Length.ToString());
        Context.Response.Write(JsonStr);
        Context.Response.End();
        return "";

    }
    /*List ebooks*/ 
    [WebMethod]
    public string Ebook()
    {
        returnType.EbookFetch obj = new returnType.EbookFetch();
        var ebookdetails = from tp in mylib.ebook_masts select tp;
        var eb = ebookdetails.ToList();
        try
        {
            obj.EBookList = new returnType.Get_EBook[ebookdetails.Count()];

            for (int i = 0; i < ebookdetails.Count(); i++)
            {

                obj.EBookList[i] = new returnType.Get_EBook();
                obj.EBookList[i].Ebk_id = string.IsNullOrEmpty(eb[i].Ebk_id.ToString()) ? "" : eb[i].Ebk_id.ToString();
                obj.EBookList[i].Ebk_name = string.IsNullOrEmpty(eb[i].Ebk_name.ToString()) ? "" : eb[i].Ebk_name.ToString();

                obj.EBookList[i].Ebk_publisher = string.IsNullOrEmpty(eb[i].Ebk_publisher.ToString()) ? "" : eb[i].Ebk_publisher.ToString();
                obj.EBookList[i].Ebk_author = string.IsNullOrEmpty(eb[i].Ebk_author.ToString()) ? "" : eb[i].Ebk_author.ToString();


                obj.EBookList[i].Ebk_coverimg = Server.MapPath(eb[i].Ebk_coverimg.ToString());
                if (obj.EBookList[i].Ebk_coverimg != null)
                {

                    Image img = Image.FromFile(obj.EBookList[i].Ebk_coverimg.ToString());
                    obj.EBookList[i].Ebk_base64img = ImageToBase64(img, System.Drawing.Imaging.ImageFormat.Png);
                }

                if (eb[i].Ebk_pdfpath.ToString() != null)
                {
                    obj.EBookList[i].Ebk_pdfpath = eb[i].Ebk_pdfpath.ToString();

                }
                obj.ResponseCode = "1";
                obj.ResponseMsg = "Successful Transaction";
            }

        }
        catch (Exception ex)
        {
            obj.ResponseCode = "2";
            obj.ResponseMsg = "Error on server side : " + ex.Message.ToString();

        }
        System.Web.Script.Serialization.JavaScriptSerializer SObj = new System.Web.Script.Serialization.JavaScriptSerializer();
        String JsonStr = SObj.Serialize(obj);
        Context.Response.ContentType = "application/json";
        Context.Response.AddHeader("content-length", JsonStr.Length.ToString());
        Context.Response.Write(JsonStr);
        Context.Response.End();
        return "";

    }
    
    public string ImageToBase64(Image image, System.Drawing.Imaging.ImageFormat format)
    {
        using (MemoryStream ms = new MemoryStream())
        {
            // Convert Image to byte[]
            image.Save(ms, format);
            byte[] imageBytes = ms.ToArray();

            // Convert byte[] to Base64 String
            string base64String = Convert.ToBase64String(imageBytes);
            return base64String;
        }
    }

	/*List books of issued by logged in students */
    [WebMethod]
    public string Status_Issue_Detail(string stud_libcard_no)
    {
        returnType.Status_Issue_Detail obj = new returnType.Status_Issue_Detail();



        var statusissuedetails = from bkmst in mylib.book_masts
                                 join transmst in mylib.trans_masts on bkmst.bk_id equals transmst.bk_id
                                 where transmst.stud_libcard_no == stud_libcard_no & transmst.trans_type.ToString().ToUpper() == "ISSUE"
                                 select new { TransactioID = transmst.trans_id, TransactionBkSeqID = transmst.bk_seq_id,TransLibCardNo = transmst.stud_libcard_no, TransIsuDate = transmst.issue_date,TransRetDate = transmst.return_date, BookID = bkmst.bk_id, BookName = bkmst.bk_name, BookAuthor = bkmst.bk_auther, BookPublisher = bkmst.bk_publisher }; 
        
        //var statusissuedetails = from tp in mylib.trans_masts where tp.stud_libcard_no == stud_libcard_no & tp.trans_type.ToString().ToUpper() == "ISSUED" select tp;
        var eb = statusissuedetails.ToList();
        
        try
        {
            if (eb.Count() > 0)
            {
                obj.IssueList = new returnType.Issue_Detail[statusissuedetails.Count()];

                for (int i = 0; i < statusissuedetails.Count(); i++)
                {

                    //if ((logindetails.SingleOrDefault().stud_enroll_no.ToString().CompareTo(stud_enroll_no)) == 0 & (logindetails.SingleOrDefault().stud_libcard_no.ToString().ToLower().CompareTo(stud_libcard_no)) == 0)
                    //            {
                    obj.IssueList[i] = new returnType.Issue_Detail();
                    obj.IssueList[i].trans_id = string.IsNullOrEmpty(eb[i].TransactioID.ToString()) ? "" : eb[i].TransactioID.ToString();
                    obj.IssueList[i].bk_id = string.IsNullOrEmpty(eb[i].BookID.ToString()) ? "" : eb[i].BookID.ToString();
                    obj.IssueList[i].bk_seq_id = string.IsNullOrEmpty(eb[i].TransactionBkSeqID.ToString()) ? "" : eb[i].TransactionBkSeqID.ToString();
                    obj.IssueList[i].bk_name = string.IsNullOrEmpty(eb[i].BookName.ToString()) ? "" : eb[i].BookName.ToString();
                    obj.IssueList[i].bk_author = string.IsNullOrEmpty(eb[i].BookAuthor.ToString()) ? "" : eb[i].BookAuthor.ToString();
                    obj.IssueList[i].bk_publisher = string.IsNullOrEmpty(eb[i].BookPublisher.ToString()) ? "" : eb[i].BookPublisher.ToString();
                    obj.IssueList[i].stud_libcard_no = string.IsNullOrEmpty(eb[i].TransLibCardNo.ToString()) ? "" : eb[i].TransLibCardNo.ToString();

                    obj.IssueList[i].issue_date = string.IsNullOrEmpty(eb[i].TransIsuDate.ToString()) ? "" : eb[i].TransIsuDate.ToString();
                    obj.IssueList[i].return_date = string.IsNullOrEmpty(eb[i].TransRetDate.ToString()) ? "" : eb[i].TransRetDate.ToString();


                    obj.ResponseCode = "1";
                    obj.ResponseMsg = "Successful Transaction";
                    //          }
                }
            }
            else
            {
                obj.ResponseCode = "3";
                obj.ResponseMsg = "Currently No Book Issued";

            }
        }
        catch (Exception ex)
        {
            obj.ResponseCode = "2";
            obj.ResponseMsg = "Error on server side : " + ex.Message.ToString();

        }
        System.Web.Script.Serialization.JavaScriptSerializer SObj = new System.Web.Script.Serialization.JavaScriptSerializer();
        String JsonStr = SObj.Serialize(obj);
        Context.Response.ContentType = "application/json";
        Context.Response.AddHeader("content-length", JsonStr.Length.ToString());
        Context.Response.Write(JsonStr);
        Context.Response.End();
        return "";

    }
	/*Book details*/
    [WebMethod]
    public string BkDetail_all()
    {
        returnType.BkDetail_ID obj = new returnType.BkDetail_ID();
        var bkdetails = from tp in mylib.book_masts select tp;
        var bd = bkdetails.ToList();
        try
        {
            if (bkdetails.Count() > 0)
            {
                obj.BkDetail = new returnType.Bk_Detail[bkdetails.Count()];

                for (int i = 0; i < bkdetails.Count(); i++)
                {
                    obj.BkDetail[i] = new returnType.Bk_Detail();
                    obj.BkDetail[i].Dbk_id = string.IsNullOrEmpty(bd[i].bk_id.ToString()) ? "" : bd[i].bk_id.ToString();
                    obj.BkDetail[i].Dbk_name = string.IsNullOrEmpty(bd[i].bk_name.ToString()) ? "" : bd[i].bk_name.ToString();
                    obj.BkDetail[i].Dbk_auther = string.IsNullOrEmpty(bd[i].bk_auther.ToString()) ? "" : bd[i].bk_auther.ToString();
                    obj.BkDetail[i].Dbk_publisher = string.IsNullOrEmpty(bd[i].bk_publisher.ToString()) ? "" : bd[i].bk_publisher.ToString();
                    obj.BkDetail[i].Dbk_desc = string.IsNullOrEmpty(bd[i].bk_desc.ToString()) ? "" : bd[i].bk_desc.ToString();
                    obj.BkDetail[i].Dbk_stock = bd[i].bk_stock.ToString();
                    obj.BkDetail[i].Dbk_stock = bd[i].bk_stock.ToString();
                    obj.BkDetail[i].Dbk_stock = bd[i].bk_stock.ToString();
                    obj.BkDetail[i].Dbk_avail = bd[i].bk_avail.ToString();
                    obj.ResponseCode = "1";
                    obj.ResponseMsg = "Successful Transaction";

                }
            }
            else
            {
                obj.ResponseCode = "3";
                obj.ResponseMsg = "No is available";

            }
        }
        catch (Exception ex)
        {
            obj.ResponseCode = "2";
            obj.ResponseMsg = "Error on server side : " + ex.Message.ToString();

        }
        System.Web.Script.Serialization.JavaScriptSerializer SObj = new System.Web.Script.Serialization.JavaScriptSerializer();
        String JsonStr = SObj.Serialize(obj);
        Context.Response.ContentType = "application/json";
        Context.Response.AddHeader("content-length", JsonStr.Length.ToString());
        Context.Response.Write(JsonStr);
        Context.Response.End();
        return "";

    }
   
    
}