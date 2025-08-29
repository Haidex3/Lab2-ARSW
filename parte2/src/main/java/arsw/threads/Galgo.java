package arsw.threads;

/**
 * Un galgo que puede correr en un carril
 * 
 * @author rlopez
 * 
 */
public class Galgo extends Thread {
	private int paso;
	private Carril carril;
	private volatile boolean paused = false; // Accesible desde RAM
	RegistroLlegada regl;

	public Galgo(Carril carril, String name, RegistroLlegada reg) {
		super(name);
		this.carril = carril;
		paso = 0;
		this.regl = reg;
	}

	public void corra() throws InterruptedException {
		while (paso < carril.size()) {
			synchronized (this) {
				while (paused) {
					wait();
				}
			}
			Thread.sleep(100);
			carril.setPasoOn(paso++);
			carril.displayPasos(paso);

			if (paso == carril.size()) {
				carril.finish();
				int ubicacion = regl.asignarPosicion(this.getName());
				System.out.println("El galgo " + this.getName() + " llego en la posicion " + ubicacion);
			}
		}
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public boolean getPaused() {
		return paused;
	}

	@Override
	public void run() {
		try {
			corra();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
