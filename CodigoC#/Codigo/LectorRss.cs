using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net;
using System.Data;

namespace Codigo
{

   
    /// RSS manager to read rss feeds

    public class RssManager
    {
    

        /// Reads the relevant Rss feed and returns a list of RssFeedItems

        public LinkedList<RssFeedItem> ReadFeed(string url)
        {
            //create a new list of the rss feed items to return
            LinkedList<RssFeedItem> rssFeedItems = new LinkedList<RssFeedItem>();

            //create an http request which will be used to retrieve the rss feed
            HttpWebRequest rssFeed = (HttpWebRequest)WebRequest.Create(url);

            //use a dataset to retrieve the rss feed
            using (DataSet rssData = new DataSet())
            {
                //read the xml from the stream of the web request
                rssData.ReadXml(rssFeed.GetResponse().GetResponseStream());

                //loop through the rss items in the dataset and populate the list of rss feed items
                foreach (DataRow dataRow in rssData.Tables["item"].Rows)
                {
                    Console.Write(dataRow["title"]);
                    Console.WriteLine();
                    Console.Write(dataRow["link"]);
                    Console.WriteLine();
                    Console.WriteLine();


                    rssFeedItems.AddLast(new RssFeedItem
                    {
                        //ChannelId = Convert.ToInt32(dataRow["channel_Id"]),
                        //Description = Convert.ToString(dataRow["description"]),
                        //ItemId = Convert.ToInt32(dataRow["item_Id"]),
                        Title = Convert.ToString(dataRow["title"]),
                        Link = Convert.ToString(dataRow["link"])
                        //PublishDate = Convert.ToDateTime(dataRow["pubDate"])
                        
                    });
                }
            }
           
            //return the rss feed items
            return rssFeedItems;
        }


        
    }
}