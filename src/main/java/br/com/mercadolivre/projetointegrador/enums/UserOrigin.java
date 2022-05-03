package br.com.mercadolivre.projetointegrador.enums;

public enum UserOrigin {
  MARKETPLACE("CUSTOMER"),
  WAREHOUSE("MANAGER");

  private final String role;

  UserOrigin(String role) {
    this.role = role;
  }

  public String getRole() {
    return role;
  }
}
