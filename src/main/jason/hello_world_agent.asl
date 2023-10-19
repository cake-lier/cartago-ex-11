!hello.

+!hello : true <-
    println("Hello world!");
    linkWorkspace("http://localhost:20100/mas/main", "w0");
    println("A");
    joinWorkspace("w0", WspId);
    println("B");
    createWorkspace("w1");
    println("C");
    quitWorkspace(WspId);
    println("D");
    linkWorkspace("http://localhost:20100/mas/main/w1", "w2");
    println("E").

{ include("$jacamoJar/templates/common-cartago.asl") }
