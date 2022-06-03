object Main1 {

  def main(args: Array[String]): Unit = {
    var c1= new checkers()
    var player:Boolean=true

    def printBoard()={
      c1.state.map(_.mkString(" ")).foreach(println)
    }

    while (true){
      printBoard()
      var in = scala.io.StdIn.readLine()
      println(c1.validate(in,player))
      player= !player
    }

  }
}