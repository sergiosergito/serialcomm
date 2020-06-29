import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JSlider;

import com.fazecast.jSerialComm.*;
public class Main {

	public static void main(String[] args) {
		JFrame window = new JFrame();
		JSlider slider = new JSlider();
		slider.setMaximum(1023);
		window.add(slider);
		window.pack();
		window.setVisible(true);
		
		SerialPort ports[] = SerialPort.getCommPorts();
		System.out.println("Select a port: ");
		int i = 1;
		for(SerialPort port : ports) {
			System.out.println(i++ + ", " + port.getSystemPortName() );
		}
		Scanner scanner = new Scanner(System.in);
		int chosenPort = scanner.nextInt();
		
		SerialPort port = ports[chosenPort - 1];
		if(port.openPort()) {
			System.out.println("Successfully opened the port.");
		} else {
			System.out.println("Unable to open the port.");
			return;
		}
		
		port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
		
		Scanner data = new Scanner(port.getInputStream());
		int number=0;
		while(data.hasNextLine()) {
			System.out.println(data.nextLine());
			try{number = Integer.parseInt(data.nextLine());}catch(Exception e) {}
			slider.setValue(number);
		}
	}

}
