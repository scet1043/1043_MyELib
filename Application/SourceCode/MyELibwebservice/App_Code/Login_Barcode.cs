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
    public class Login_Barcode : GenericClass
    {
        public Login_Barcode()
        {
            //
            // TODO: Add constructor logic here
            //
        }

        public string stud_enroll_no { get; set; }
        public string stud_libcard_no { get; set; }
        public string stud_fname { get; set; }
        public string stud_mname { get; set; }
        public string stud_lname { get; set; }
        public string stud_class { get; set; }
        public string stud_dept { get; set; }
        public string stud_email { get; set; }
        public string stud_mob_no { get; set; }

    }
}