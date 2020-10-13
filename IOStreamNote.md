# IO流

1. **File类的使用**

   

2. **IO流原理及流的分类**

   |        分类        |      字节输入流      |      字节输出流       |    字符输入流     |     字符输出流     |
| :----------------: | :------------------: | :-------------------: | :---------------: | :----------------: |
   |      抽象基类      |     InputStream      |     OutputStream      |      Reader       |       Writer       |
   | 访问文件（节点流） |   FileInputStream    |   FileOutputStream    |    FileReader     |     FileWriter     |
   |      访问数组      | ByteArrayInputStream | ByteArrayOutputStream |  CharArrayReader  |  CharArrayWriter   |
   |      访问管道      |   PipedInputStream   |   PipedOutputStream   |    PipedReader    |    PipedWriter     |
   |     访问字符串     |                      |                       |   StringReader    |    StringWriter    |
   |       缓冲流       | BufferedInputStream  | BufferedOutputStream  |  BufferedReader   |   BufferedWriter   |
   |       转换流       |                      |                       | InputStreamReader | OutputStreamWriter |
   |       对象流       |  ObjectInputStream   |  ObjectOutputStream   |                   |                    |
   |                    |   FileInputStream    |   FileOutputStream    |    FileReader     |     FileWriter     |
   |       打印流       |                      |      PrintStream      |                   |    PrintWriter     |
   |     推回输入流     | PushbackInputStream  |                       |  PushbackReader   |                    |
   |       特殊流       |   DataInputStream    |   DataOutputStream    |                   |                    |
   
   

3. **节点流（或文件流）**

4. **缓冲流**

5. **转换流**

6. **标准输入、输出流**

7. **打印流**

8. **数据流**

9. **对象流**

10. **随机存取文件流**

11. **NIO.2中Path、Paths、File类的使用**