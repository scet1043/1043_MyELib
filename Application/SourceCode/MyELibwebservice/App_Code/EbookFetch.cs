using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;



namespace returnType
{
    public class EbookFetch : GenericClass
    {

        public Get_EBook[] EBookList { get; set; }
        public EbookFetch()
        {
            //
            // TODO: Add constructor logic here
            //
        }
       
        

    }
    public class Get_EBook
    {
        public string Ebk_id { get; set; }
        public string Ebk_name { get; set; }
        public string Ebk_coverimg { get; set; }
        public string Ebk_pdfpath { get; set; }
        public string Ebk_publisher { get; set; }
        public string Ebk_author { get; set; }
        public string Ebk_base64img { get; set; }
       
    }
}