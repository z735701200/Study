package chapter7;


import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class HelloMainSimple {
	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("Hello",ConfigFactory.load("samplehello.conf"));
		ActorRef a = system.actorOf(Props.create(HelloWorld.class),"helloWorld");
		System.out.println("HelloWord Actor Path:" + a.path());
	}
}
