package realise.example.realiseexample;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.*;
import java.util.Properties;

public class Sender {

    public static void main(String[] args) {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        String xmlfile = null;
        try(BufferedReader br = new BufferedReader(new FileReader("excercise.xml"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            xmlfile = sb.toString();
            System.out.println(xmlfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        connectionFactory.setBrokerURL("tcp://localhost:61616");

        try
        {   //Create and start connection
            Properties props = new Properties();
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            props.setProperty(Context.PROVIDER_URL,"tcp://localhost:61616");
            InitialContext ctx = new InitialContext(props);

            TopicConnectionFactory f = (TopicConnectionFactory)ctx.lookup("TopicConnectionFactory");
            TopicConnection con = f.createTopicConnection();
            con.start();
            TopicSession ses=con.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic t=(Topic)ctx.lookup("myTopic");
            TopicPublisher publisher=ses.createPublisher(t);
            TextMessage msg=ses.createTextMessage();

            System.out.println("Enter Msg, end to terminate:");
            msg.setText(xmlfile);
            publisher.publish(msg);
            System.out.println("Message successfully sent.");

            con.close();
        }catch(Exception e){System.out.println(e);}
    }
}
