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
    public class BkDetail_Publisher : GenericClass
    {
        public Bk_Detail_Publisher[] BkDetailP { get; set; }
        public BkDetail_Publisher()
        {
            //
            // TODO: Add constructor logic here
            //
        }
    }
    public class Bk_Detail_Publisher
    {
        public string Dbk_id { get; set; }
        public string Dbk_name { get; set; }
        public string Dbk_auther { get; set; }
        public string Dbk_publisher { get; set; }
        public string Dbk_desc { get; set; }
        public string Dbk_stock { get; set; }
        public string Dbk_avail { get; set; }
    }
}

