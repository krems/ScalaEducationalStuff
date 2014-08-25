package example

import java.util.Date

import scala.actors.{TIMEOUT, Actor}

/**
 * Created by Java Student on 8/21/2014.
 */
abstract class AuctionMessage
case class Offer(bid: Int, client: Actor) extends AuctionMessage
case class Inquire(client: Actor) extends AuctionMessage

abstract class AuctionReply
case class Status(asked: Int, expire: Date, maxBidder: Actor) extends AuctionReply
case class BeatenOffer(maxBid: Int) extends AuctionReply
case class AuctionConcluded(seller: Actor, client: Actor) extends AuctionReply
case object BestOffer extends AuctionReply
case object AuctionFailed extends AuctionReply
case object AuctionOver extends AuctionReply

class Auction(seller: Actor, minBid: Int, closing: Date) extends Actor {
  val timeToShutdown = 5000
  val bidIncrement = 10
  def act() = {
    var maxBid = minBid - bidIncrement
    var maxBidder: Actor = null
    var running = true
    while (running) {
      val cls = closing.getTime - new Date().getTime
      if (cls > 0) {
        receiveWithin(cls) {
          case Offer(bid, client) =>
            println("Auction: offer received. bid = " + bid)
            if (bid >= maxBid + bidIncrement) {
              if (maxBid >= minBid) {
                maxBidder ! BeatenOffer(bid)
              }
              maxBid = bid
              maxBidder = client
              client ! BestOffer
            }
          case Inquire(client) =>
            println("Auction: inquired " + client)
            client ! Status(maxBid, closing, maxBidder)
          case TIMEOUT =>
            println("Auction: timeout")
            if (maxBid >= minBid) {
              val reply = AuctionConcluded(seller, maxBidder)
              maxBidder ! reply
              seller ! reply
            } else {
              seller ! AuctionFailed
            }
            receiveWithin(timeToShutdown) {
              case Offer(_, client) =>
                println("Auction: offer after timeout")
                client ! AuctionOver
              case TIMEOUT =>
                println("Auction: timeout timeout")
                running = false
            }
        }
      } else {
        running = false
      }
    }
  }
}

class Seller(closing: Date) extends Actor {
  def act(): Unit = {
    val cls = closing.getTime - new Date().getTime
    if (cls > 0) {
      receiveWithin(cls + 1000) {
        case AuctionConcluded(me, maxBidder) =>
          println("Seller: Auction concluded, max bidder is " + maxBidder)
        case AuctionFailed =>
          println("Seller: Auction failed")
        case TIMEOUT =>
          println("Seller: timeout")
      }
    }
  }
}

class Client(auction: Actor, bidInc: Int, name: String) extends Actor {
  var bid = 0
  def act(): Unit = {
    auction ! Inquire(this)
    println("Client: " + this.toString + " inquires")
    var running = true
    var closing: Long = 2000
    while (running) {
      receiveWithin(closing) {
        case BestOffer =>
          println("Client: " + this.toString + " best offer. bid = " + bid)
          Thread sleep 1000
          auction ! Inquire(this)
        case Status(maxBid, clos, maxBidder) =>
          println("Client: " + this.toString + " status got. bid = " + bid + " maxBid = " + maxBid + " maxBidder: " + maxBidder)
          if (this == maxBidder) {
            println("Client: " + this.toString + " sleeping")
            Thread sleep 1000
            auction ! Inquire(this)
          } else {
            if (maxBid > bid) {
              bid = maxBid + bidInc
              closing = clos.getTime - new Date().getTime
              if (closing <= 0) {
                running = false
              }
              println("Client: " + this.toString + " new bid")
              auction ! Offer(bid, this)
            }
            auction ! Inquire(this)
          }
        case TIMEOUT =>
          println("Client: " + this.toString + " timeout")
          running = false
      }
    }
  }
  override def toString: String = {
    "Client " + name + " with bid = " + bid
  }
}

object AuctionActors {
  def main(args: Array[String]) {
    val closing = new Date(System.currentTimeMillis() + 30000)
    val seller = new Seller(closing)
    val auction = new Auction(seller, 100, closing)
    val clientStingy = new Client(auction, 50, "Vasya")
    val clientLavish = new Client(auction, 100, "Petya")
    seller.start()
    auction.start()
    clientStingy.start()
    clientLavish.start()
  }
}
