using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;



namespace returnType
{
    public class Status_Issue_Detail : GenericClass
    {

        public Issue_Detail[] IssueList { get; set; }
        public Status_Issue_Detail()
        {
            //
            // TODO: Add constructor logic here
            //
        }
       
        

    }
    public class Issue_Detail
    {
        public string trans_id { get; set; }
        public string bk_id { get; set; }
        public string bk_seq_id { get; set; }
        public string bk_name { get; set; }
        public string bk_author { get; set; }
        public string bk_publisher { get; set; }            
       
        public string stud_libcard_no { get; set; }
        public string issue_date { get; set; }
        public string return_date { get; set; }
      
       
    }
}