package com.thanple.thinking.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Selector_demo {
	// 通道管理器
	private Selector selector;

	public void initServer(int port) throws Exception {
		// 获得一个ServerSocket通道
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		// 设置通道为 非阻塞
		serverChannel.configureBlocking(false);
		// 将该通道对于的serverSocket绑定到port端口
		serverChannel.socket().bind(new InetSocketAddress(port));
		// 获得一耳光通道管理器
		this.selector = Selector.open();

		// 将通道管理器和该通道绑定，并为该通道注册selectionKey.OP_ACCEPT事件
		// 注册该事件后，当事件到达的时候，selector.select()会返回，
		// 如果事件没有到达selector.select()会一直阻塞

		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
	}

	// 采用轮训的方式监听selector上是否有需要处理的事件，如果有，进行处理
	public void listen() throws Exception {
		System.out.println("start server");
		// 轮询访问selector
		while (true) {
			// 当注册事件到达时，方法返回，否则该方法会一直阻塞
			selector.select();
			// 获得selector中选中的相的迭代器，选中的相为注册的事件
			Iterator<?> ite = this.selector.selectedKeys().iterator();
			while (ite.hasNext()) {
				SelectionKey key = (SelectionKey) ite.next();
				// 删除已选的key 以防重负处理
				ite.remove();
				// 客户端请求连接事件
				if (key.isAcceptable()) {
					ServerSocketChannel server = (ServerSocketChannel) key
							.channel();
					// 获得和客户端连接的通道
					SocketChannel channel = server.accept();
					// 设置成非阻塞
					channel.configureBlocking(false);
					// 在这里可以发送消息给客户端
					channel.write(ByteBuffer.wrap(new String("hello client")
							.getBytes()));
					// 在客户端 连接成功之后，为了可以接收到客户端的信息，需要给通道设置读的权限
					channel.register(this.selector, SelectionKey.OP_READ);
					// 获得了可读的事件

				} else if (key.isReadable()) {
					read(key);
				}

			}
		}
	}

	// 处理 读取客户端发来的信息事件
	private void read(SelectionKey key) throws Exception {
		// 服务器可读消息，得到事件发生的socket通道
		SocketChannel channel = (SocketChannel) key.channel();
		// 穿件读取的缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(10);
		channel.read(buffer);
		byte[] data = buffer.array();
		String msg = new String(data).trim();
		System.out.println("server receive from client: " + msg);
		ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
		channel.write(outBuffer);
	}

	public static void main(String[] args) throws Throwable {
		Selector_demo server = new Selector_demo();
		server.initServer(8989);
		server.listen();
	}
}



class Selector_Client{
	
	 // 通道管理器  
    private Selector selector;  
  
    /** 
     * * // 获得一个Socket通道，并对该通道做一些初始化的工作 * @param ip 连接的服务器的ip // * @param port 
     * 连接的服务器的端口号 * @throws IOException 
     */  
    public void initClient(String ip, int port) throws IOException { // 获得一个Socket通道  
        SocketChannel channel = SocketChannel.open(); // 设置通道为非阻塞  
        channel.configureBlocking(false); // 获得一个通道管理器  
        this.selector = Selector.open(); // 客户端连接服务器,其实方法执行并没有实现连接，需要在listen()方法中调  
        // 用channel.finishConnect();才能完成连接  
        channel.connect(new InetSocketAddress(ip, port));  
        // 将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_CONNECT事件。  
        channel.register(selector, SelectionKey.OP_CONNECT);  
    }  
  
    /** 
     * * // 采用轮询的方式监听selector上是否有需要处理的事件，如果有，则进行处理 * @throws // IOException 
     * @throws Exception  
     */  
    public void listen() throws Exception { // 轮询访问selector  
        while (true) {  
            // 选择一组可以进行I/O操作的事件，放在selector中,客户端的该方法不会阻塞，  
            // 这里和服务端的方法不一样，查看api注释可以知道，当至少一个通道被选中时，  
            // selector的wakeup方法被调用，方法返回，而对于客户端来说，通道一直是被选中的  
            selector.select(); // 获得selector中选中的项的迭代器  
            Iterator<?> ite = this.selector.selectedKeys().iterator();  
            while (ite.hasNext()) {  
                SelectionKey key = (SelectionKey) ite.next(); // 删除已选的key,以防重复处理  
                ite.remove(); // 连接事件发生  
                if (key.isConnectable()) {  
                    SocketChannel channel = (SocketChannel) key.channel(); // 如果正在连接，则完成连接  
                    if (channel.isConnectionPending()) {  
                        channel.finishConnect();  
                    } // 设置成非阻塞  
                    channel.configureBlocking(false);  
                    // 在这里可以给服务端发送信息哦  
                    channel.write(ByteBuffer.wrap(new String("hello server!").getBytes()));  
                    // 在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。  
                    channel.register(this.selector, SelectionKey.OP_READ); // 获得了可读的事件  
                } else if (key.isReadable()) {  
                    read(key);  
                }  
            }  
        }  
    }  
  
    private void read(SelectionKey key) throws Exception {  
        SocketChannel channel = (SocketChannel) key.channel();  
        // 穿件读取的缓冲区  
        ByteBuffer buffer = ByteBuffer.allocate(10);  
        channel.read(buffer);  
        byte[] data = buffer.array();  
        String msg = new String(data).trim();  
        System.out.println("client receive msg from server:" + msg);  
        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());  
        channel.write(outBuffer);  
  
    }  
  
    /** 
     * * // 启动客户端测试 * @throws IOException 
     * @throws Exception  
     */  
    public static void main(String[] args) throws Exception {  
    	Selector_Client client = new Selector_Client();  
        client.initClient("localhost", 8989);  
        client.listen();  
    }  
}





