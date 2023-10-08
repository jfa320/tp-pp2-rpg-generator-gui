package tp.pp2.rpg.experience.gui.controller;

import java.util.Observable;
import java.util.Observer;

import tp.pp2.rpg.experience.core.entidades.Batalla;
import tp.pp2.rpg.experience.core.entidades.estados.EstadoBatalla;
import tp.pp2.rpg.experience.core.entidades.rpg.experience.ObtenedorGanador;
import tp.pp2.rpg.experience.gui.view.PanelCombate;

public class PanelCombateController implements Observer {
	private PanelCombate panelCombate;
	private Batalla batalla;

	public PanelCombateController(PanelCombate panelCombate, Batalla batalla) {
		this.panelCombate = panelCombate;
		this.batalla = batalla;
		this.initialize();
	}

	private void initialize() {
		batalla.addObserver(this);
		asignarNombreHabilidadesBotones();
		asignarFuncionalidadHabilidadesBotones();
		bloquearBotonesSinUso();
	}

	private void asignarNombreHabilidadesBotones() {
		for (int i = 0; i < this.panelCombate.getBotonesHabilidades().size(); i++) {
			if (batalla.getHabilidades().size() > i) {
				this.panelCombate.getBotonesHabilidades().get(i)
						.setText(batalla.getHabilidades().get(i).getNombre()+" | "+batalla.getHabilidades().get(i).getDescripcion());
			}
		}
	}

	private void asignarFuncionalidadHabilidadesBotones() {
		for (int i = 0; i < this.panelCombate.getBotonesHabilidades().size(); i++) {
			final int index = i;
			if (index < batalla.getHabilidades().size()) {
				this.panelCombate.getBotonesHabilidades().get(index).addActionListener(e -> {
					try {
						batalla.jugar(batalla.getHabilidades().get(index));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
			}
		}
	}

	private void bloquearBotonesSinUso() {
		// TODO Auto-generated method stub
		this.panelCombate.getBotonesHabilidades().forEach(btn->{
			if(btn.getActionListeners().length==0) {
				btn.setEnabled(false);
			}
		});
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if(batalla.getEstado().equals(EstadoBatalla.FINALIZADA)) {
			String ganador=new ObtenedorGanador().obtenerGanador(batalla);
			String mensajeVictoria="Gana " + ganador;
			this.panelCombate.setResultadoCombate(mensajeVictoria);
			this.panelCombate.getBotonesHabilidades().forEach(btn -> btn.setEnabled(false));
		}
	}

}
