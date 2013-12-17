using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

/// <summary>
/// Summary description for Login
/// </summary>
/// 

namespace returnType
{
    public class Insert_Request : GenericClass
    {
        public Insert_Request()
        {
            //
            // TODO: Add constructor logic here
            //
        }

        public string bk_id { get; set; }
        public string stud_enroll_no { get; set; }
        public string stud_libcard_no { get; set; }
        public string req_date { get; set; }
        public string req_status { get; set; }
    }
}