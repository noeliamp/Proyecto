using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Web;
using System.Net;
using System.IO.Compression;
using System.Text.RegularExpressions;
using HtmlAgilityPack;

namespace Codigo
{
class Comun {


    public string Parseado(string dir, string patern)
    {

        

        WebClient html = new WebClient();
        string h = html.DownloadString(dir);

        HtmlDocument doc = new HtmlDocument();
        doc.LoadHtml(h);

        HtmlNode content = doc.GetElementbyId(patern);
        var forms = content.Descendants().Where(node => node.Name == "p");

        string text = "";

        foreach(HtmlNode node in forms){
            text += node.InnerText;

        }

        return text;
    }

}
}