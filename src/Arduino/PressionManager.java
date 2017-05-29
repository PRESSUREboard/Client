package Arduino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import KeystrokeMeasuring.TimingManager;
import jssc.SerialPortException;

public class PressionManager implements Runnable {

	private ArrayList<Double> tabTriee;
	private ArduinoUsbChannel vcpChannel;
	private final Console console;
	private final TimingManager tm;
	private boolean stop;
	private boolean end = false;

	public PressionManager(TimingManager tm) {

		console = new Console();

		console.log("DEBUT du programme TestArduino !..");

		setStop(false);

		this.tm = tm;

		String port = null;

		// Recherche du port de l'Arduino
<<<<<<< HEAD
		do {

			console.log("RECHERCHE d'un port disponible...");
			port = ArduinoUsbChannel.getOneComPort();

			if (port == null) {
				console.log("Aucun port disponible!");
				console.log("Nouvel essai dans 5s");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException ex) {
					// Ignorer l'Exception
				}
			}

		} while (port == null);
		port = "COM4";

		console.println("Connection au Port " + port);
		try {

			vcpChannel = new ArduinoUsbChannel(port);
			/*
			 * if(!(vcpChannel.serialPort.isOpened())){ System.out.
			 * println("Impossible de se connecter au module de mesure de pressions, poursuite du programme "
			 * + "sans mesure de pressions"); tm.setArduinoConnected(false);
			 * this.setEnd(false); }
			 */

		} catch (IOException ex) {
			ex.printStackTrace(System.err);
		}

	}

	@Override
	public void run() {

		try {
			vcpChannel.open();
		} catch (SerialPortException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ArrayList<Mesure> tabMesures = new ArrayList<Mesure>(); // mesures
																// brutes de
																// pression

		BufferedReader vcpInput = new BufferedReader(new InputStreamReader(vcpChannel.getReader()));

		while (!stop && tm.isArduinoConnected()) {

			try {

				// Attend le debut de la lecture des donnees par le clavier
				synchronized (tm) {
					try {
						tm.wait();
						setEnd(false);
					} catch (InterruptedException ie) {
					}
				}

				if (true) {

					String line;

					while (!end) {
						try {
							if ((line = vcpInput.readLine()) != null) {
								insertionTab(line, tabMesures);
								console.println("Data from Arduino: " + line);
							}
						} catch (java.io.InterruptedIOException e) {
						}

					}

					System.out.println("Sortie boucle");

					triTab(tabMesures);
				}

			} catch (IOException e) {
			}

=======

		console.log("RECHERCHE d'un port disponible...");
		port = ArduinoUsbChannel.getOneComPort();

		if (port == null) {
			System.out.println("Impossible de se connecter au module de mesure de pressions, poursuite du programme "
					+ "sans mesure de pressions");
			tm.setArduinoConnected(false);
			this.setEnd(true);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException ex) {
				// Ignorer l'Exception
			}
		}
	}

	@Override
	public void run() {
		BufferedReader vcpInput = null;
		ArrayList<Mesure> tabMesures = null;
		if (!(end)) {
			try {
				vcpChannel.open();
			} catch (SerialPortException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			tabMesures = new ArrayList<Mesure>(); // mesures
																	// brutes de
																	// pression

			vcpInput = new BufferedReader(new InputStreamReader(vcpChannel.getReader()));
		}
		while (!stop && tm.isArduinoConnected()) {

			try {

				// Attend le debut de la lecture des donnees par le clavier
				synchronized (tm) {
					try {
						System.out.println("Waiting!");
						tm.wait();
						setEnd(false);
						System.out.print("Done waiting!");
					} catch (InterruptedException ie) {
					}
				}

				if (true) {

					String line;
					try {
						while (!Thread.interrupted() && end == false) {

							if ((line = vcpInput.readLine()) != null) {
								insertionTab(line, tabMesures);
								console.println("Data from Arduino: " + line);
							}

						}
					} catch (java.io.InterruptedIOException e) {
						System.out.println("meow");
					}

					System.out.println("Sortie boucle");

					triTab(tabMesures);
					// afficherTabTriee();

				}

			} catch (IOException e) {
				e.printStackTrace(System.err);
			}

>>>>>>> refs/remotes/P2i-2-222B-ClavierIdentificateur/Analyse
		}

		try {
			vcpInput.close();
			vcpChannel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NullPointerException e){
			
		}

	}

	public void insertionTab(String s, ArrayList<Mesure> tab) {

		String[] temp = s.split("_");
		System.out.println(s);

		char ident = temp[0].charAt(0);
		double p = Double.parseDouble(temp[1]);
		int cmpt = Integer.parseInt(temp[2]);

		tab.add(new Mesure(cmpt, p, ident));

	}

	public void triTab(ArrayList<Mesure> t) {

		ArrayList<Double> m = new ArrayList<Double>();

		int cmpt = t.get(0).compt;
		double pres = t.get(0).pression;
		char ident = t.get(0).id;

		for (int i = 0; i < t.size(); i++) {

			if (t.get(i).compt == cmpt && t.get(i).id == ident && t.get(i).pression >= pres)
				pres = t.get(i).pression;
			else {
				m.add(pres);
				ident = t.get(i).id;
				pres = t.get(i).pression;
				cmpt = t.get(i).compt;
			}

		}

		m.add(pres);

		tabTriee = new ArrayList<Double>(m);

	}

	public void afficherTabTriee() {

		for (Double m : tabTriee)
			System.out.println(m);

	}

	public ArrayList<Double> getTabTriee() {
		return tabTriee;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

}
