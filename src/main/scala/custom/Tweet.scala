package custom

import net.liftweb.json.Extraction.decompose
import net.liftweb.json.{DefaultFormats, Formats, compact, render}

/**
  * Created by akash on 28/6/17.
  */
case class Tweet(user: String, tweet: String) {

  implicit val formats: Formats = DefaultFormats

  def getFilteredTweet: String = {
    val user = new String(this.user.toCharArray.filter(_.toString.matches("^[a-zA-z0-9]*$")))
    val tweet = new String(this.tweet.toCharArray.filter(_.toString.matches("^[a-zA-z0-9]*$")))
    val filterTweet = Tweet(user, tweet)
    compact(render(decompose(filterTweet)))
  }
}
