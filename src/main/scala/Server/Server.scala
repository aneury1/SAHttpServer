
class AhttpServer(port : Int = 8080) {

    var server: ServerSocket = null
    var handler:RequestHandler = null
    type RequestHandler = (Socket) => Unit

    def default_response_server(s: Socket )={
        var bytes = new Array[Byte](4096)
        var input = s.getInputStream()
        var output = s.getOutputStream()
        var read = input.read(bytes)
        var response = "HTTP/1.1 200 OK\r\n" +
            "Content-Type: text/html\r\n" +
            "Content-Length: " + read + "\r\n" +
            "\r\n" +
            new String(bytes, 0, read)
        output.write(response.getBytes())
    }

    def createServer(requestHandler: RequestHandler= default_response_server)={
        server = new ServerSocket(port)
        this.handler = requestHandler
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
            default_response_server(client)
            client.close()
        }
    }
}