package br.com.mercadolivre.projetointegrador.warehouse.view;

public class SectionView {
  public interface Detail {}

  public interface SectionBatches {}

  public interface SectionWithBatches extends SectionBatches, BatchView.BatchSection {}
}
