
class AhttpServer(port : Int = 8080) {

    var server: ServerSocket = null
    var handler:RequestHandler = null
    type RequestHandler = (Socket) => Unit
    def default_response_server(s: Socket )={

    }

    def createServer(requestHandler: RequestHandler= default_response_server)={
        server = new ServerSocket(port)
        if(server==null){
            println("Server is not created")
        }else{
            println(s"Server is created ${server.getInetAddress()}")
        }
    }

    def listen()={
        println(s"Listening on port => ${port}")

        var client = server.accept()
        if(this.handler!=null){
            this.handler(client)
        }else{
            println("No handler is set")
            client.close()
        }
    }
}