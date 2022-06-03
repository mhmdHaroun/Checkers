class checkers {

  var state=Array(
    Array("\u001b[44m,0","\u001b[46m,\u001b[38mo","\u001b[44m,0","\u001b[46m,\u001b[38mo","\u001b[44m,0","\u001b[46m,\u001b[38mo","\u001b[44m,0","\u001b[46m,\u001b[38mo"),

    Array("\u001b[46m,\u001b[38mo","\u001b[44m,0","\u001b[46m,\u001b[38mo","\u001b[44m,0","\u001b[46m,\u001b[38mo","\u001b[44m,0","\u001b[46m,\u001b[38mo","\u001b[44m,0"),

    Array("\u001b[44m,0","\u001b[46m,\u001b[38mo","\u001b[44m,0","\u001b[46m,\u001b[38mo","\u001b[44m,0","\u001b[46m,\u001b[38mo","\u001b[44m,0","\u001b[46m,\u001b[38mo"),

    Array("\u001b[46m,0","\u001b[44m,0","\u001b[46m,0","\u001b[44m,0","\u001b[46m,0","\u001b[44m,0","\u001b[46m,0","\u001b[44m,0"),

    Array("\u001b[44m,0","\u001b[46m,0","\u001b[44m,0","\u001b[46m,0","\u001b[44m,0","\u001b[46m,0","\u001b[44m,0","\u001b[46m,0"),

    Array("\u001b[46m,\u001b[30mo","\u001b[44m,0","\u001b[46m,\u001b[30mo","\u001b[44m,0","\u001b[46m,\u001b[30mo","\u001b[44m,0","\u001b[46m,\u001b[30mo","\u001b[44m,0"),

    Array("\u001b[44m,0","\u001b[46m,\u001b[30mo","\u001b[44m,0","\u001b[46m,\u001b[30mo","\u001b[44m,0","\u001b[46m,\u001b[30mo","\u001b[44m,0","\u001b[46m,\u001b[30mo"),

    Array("\u001b[46m,\u001b[30mo","\u001b[44m,0","\u001b[46m,\u001b[30mo","\u001b[44m,0","\u001b[46m,\u001b[30mo","\u001b[44m,0","\u001b[46m,\u001b[30mo","\u001b[44m,0")

  )

  def location(input:String):Array[Int]= {
    var res = Array(0,0)
    //if ((input.length == 4) && input.charAt(1).isLetter && input.charAt(3).isLetter && input.charAt(0).isDigit && input.charAt(2).isDigit){
    input.charAt(1) match {
      case 'a'|'A' =>
        res(1) = 0

      case 'b'|'B' =>
        res(1) = 1

      case 'c'|'C' =>
        res(1) = 2

      case 'd'|'D' =>
        res(1) = 3

      case 'e'|'E' =>
        res(1) = 4

      case 'f'|'F' =>
        res(1) = 5

      case 'g'|'G' =>
        res(1) = 6

      case 'h'|'H' =>
        res(1) = 7

      case _ =>
        res(1) = -1
    }
    res(0) = ('9' - input.charAt(0)) - 1
    if(res(0)>8 && res(0)<0)
      res(0) = -1
    res
  }

  def validate(input: String,player : Boolean): Boolean ={
    // get the input in shape of  four Int r1, c1, r2, c2
    var start = Array(0,0)
    var end = Array(0,0)
    if ((input.length == 4) && input.charAt(1).isLetter && input.charAt(3).isLetter && input.charAt(0).isDigit && input.charAt(2).isDigit) {
      start = location(input.slice(0, 2))
      end = location(input.slice(2, 4))
      if (start(0) < 0 || end(0) < 0 || start(1) < 0 || end(1) < 0) {
        return false
      }
    }
    var r1 = start(0)
    var c1 = start(1)
    var r2 = end(0)
    var c2 = end(1)
    if(player){
      if(!state(r1)(c1).contains("38"))
        return false
    }
    else
      if(!state(r1)(c1).contains("30"))
        return false
    var res = validate(state,r1, c1, r2, c2, player)
    if (res && math.abs(r1-r2) == 2){
      if (canEat(r2,c2,player)){
        //print("here")
        //state.map(_.mkString(" ")).foreach(println)
        secondEat(r2,c2,player)
        return true
      }
    }
    res
  }



  def secondEat(r :Int, c:Int, player:Boolean): Unit ={
    var res = false
    var end = Array(0,0)
    var r1 = r
    var c1 = c
    while (!res){
      end = in()
      if(math.abs(end(0)-r1)==2) {
        res = validate(state, r1, c1, end(0), end(1), player)
        if (res){

          res = !canEat(end(0),end(1),player)
          r1 = end(0)
          c1 = end(1)
        }
      }

    }
  }
  def in():Array[Int] ={
    var end = Array(-1,-1)
    while (true){
      var in = scala.io.StdIn.readLine()
      if(in.length==2)
        end = location(in)
      if(end(0)> -1 && end(1) > -1) {
        //print(end(1))
        //print(end(0))
        return end
      }
    }
    return end
  }

  def copy(): Array[Array[String]] ={
    var res = new Array[Array[String]](8)
    var i = 0
    while (i<8) {
      res(i) = state(i) map(identity)
      i += 1
    }
    res
  }

  def canEat(r:Int,c:Int,player:Boolean): Boolean ={
    //print("here")
    var temp = copy()
    var temp1 = copy()
    if (player){
      return validate(temp,r, c, r+2, c - 2, player)||validate(temp1,r, c, r+2, c + 2, player)
    }
    else
      return validate(temp,r, c, r-2, c - 2, player)||validate(temp1,r, c, r-2, c + 2, player)
  }

  def validate(state : Array[Array[String]],r1:Int,c1:Int,r2:Int,c2:Int,player:Boolean):Boolean={

    if(c2>7||c2<0||r2>7||r2<0)
      return false
    if (state(r2)(c2).charAt(state(r2)(c2).length-1)!='0')
      return false

    if (player){          //player 1
      if(state(r1)(c1).contains("38")&&state(r1)(c1).charAt(state(r1)(c1).length-1)=='o'){           //chooce his pawn
        if((r2-r1)==1&&math.abs(c2-c1)==1&&state(r2)(c2).charAt(state(r2)(c2).length-1)=='0'){       // validate regular move
          if(r2==7){                                                                                 //be king
            var temp=state(r2)(c2)
            state(r2)(c2)=state(r1)(c1).replace("o", "K")
            state (r1)(c1)=temp
            return true
          }
          else{
            var temp=state(r2)(c2)
            state(r2)(c2)=state(r1)(c1)
            state (r1)(c1)=temp
            return true
          }
        }
        else if((r2-r1==2)&&(math.abs(c1-c2)==2)&&(state((r1+r2)/2)((c1+c2)/2).contains("30"))){                    //eat
          state((r1+r2)/2)((c1+c2)/2)="\u001b[46m,0"             //remove the different colored piece
          //print(c2)
          //print(r2)
          if(r2==7){                                    //move the controlled piece  //be king
            var temp=state(r2)(c2)
            state(r2)(c2)=state(r1)(c1).replace("o", "K")
            state (r1)(c1)=temp
            return true
          }
          else{                                         //only move
            var temp=state(r2)(c2)
            state(r2)(c2)=state(r1)(c1)
            state (r1)(c1)=temp
            return true
          }
        }
        else {
          return false
        }

      }
      else if(state(r1)(c1).contains("38")&&state(r1)(c1).charAt(state(r1)(c1).length-1) == 'K'){    //chooce king
        if(math.abs(r2-r1)==1&&math.abs(c2-c1)==1&&state(r2)(c2).charAt(state(r2)(c2).length-1)=='0'){       // validate regular move
          var temp=state(r2)(c2)
          state(r2)(c2)=state(r1)(c1)
          state (r1)(c1)=temp
          return true
        }
        else if((math.abs(r2-r1)==2)&&(math.abs(c1-c2)==2)&&(state((r1+r2)/2)((c1+c2)/2).contains("30"))){                    //eat
          state((r1+r2)/2)((c1+c2)/2)="\u001b[46m,0"             //remove the different colored piece
          //print(c2)
          //print(r2)
          var temp=state(r2)(c2)
          state(r2)(c2)=state(r1)(c1)
          state (r1)(c1)=temp
          return true
        }
        else{
          return false                                                    //the move is invalid
        }
      }
      else{return false}
    }
    else{                  //player 2 will be the same as player 1 but change every 30->38, r2==7 insteed of 0 to be king, (r2-r1) insteed of (r1-r2)
      if(state(r1)(c1).contains("30")&&state(r1)(c1).charAt(state(r1)(c1).length-1)=='o'){           //chooce his pawn
        if((r1-r2)==1&&math.abs(c2-c1)==1&&state(r2)(c2).charAt(state(r2)(c2).length-1)=='0'){       // validate regular move
          if(r2==0){     //be king
            var temp=state(r2)(c2)
            state(r2)(c2)=state(r1)(c1).replace("o", "K")
            state (r1)(c1)=temp
            return true
          }
          else{
            var temp=state(r2)(c2)
            state(r2)(c2)=state(r1)(c1)
            state (r1)(c1)=temp
            return true
          }
        }
        else if((r1-r2==2)&&(math.abs(c1-c2)==2)&&(state((r1+r2)/2)((c1+c2)/2).contains("38"))){                    //eat
          state((r1+r2)/2)((c1+c2)/2)="\u001b[46m,0"             //remove the different colored piece
          //print(c2)
          //print(r2)
          if(r2==0){                                    //move the controlled piece  //be king
            var temp=state(r2)(c2)
            state(r2)(c2)=state(r1)(c1).replace("o", "K")
            state (r1)(c1)=temp
            return true
          }
          else{                                         //only move
            var temp=state(r2)(c2)
            state(r2)(c2)=state(r1)(c1)
            state (r1)(c1)=temp
            return true
          }
        }
        else{return false}
      }
      else if(state(r1)(c1).contains("30")&&state(r1)(c1).charAt(state(r1)(c1).length-1) == 'K'){    //chooce king
        if(math.abs(r1-r2)==1&&math.abs(c2-c1)==1&&state(r2)(c2).charAt(state(r2)(c2).length-1)=='0'){       // validate regular move
          var temp=state(r2)(c2)
          state(r2)(c2)=state(r1)(c1)
          state (r1)(c1)=temp
          return true
        }
        else if((math.abs(r1-r2)==2)&&(math.abs(c1-c2)==2)&&(state((r1+r2)/2)((c1+c2)/2).contains("38"))){                    //eat
          state((r1+r2)/2)((c1+c2)/2)="\u001b[46m,0"             //remove the different colored piece
          //print(c2)
          //print(r2)
          var temp=state(r2)(c2)
          state(r2)(c2)=state(r1)(c1)
          state (r1)(c1)=temp
          return true
        }
        else{return false}
      }
      else{return false}
    }
  }
}