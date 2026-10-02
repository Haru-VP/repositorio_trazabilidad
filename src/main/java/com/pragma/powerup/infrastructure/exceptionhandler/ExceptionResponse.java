package com.pragma.powerup.infrastructure.exceptionhandler;

public enum ExceptionResponse {
    NO_DATA_FOUND("No data found for the requested petition"),
    PEDIDO_NO_ENCONTRADO("No se encontraron registros de trazabilidad para el pedido especificado"),
    USUARIO_NO_AUTORIZADO("El usuario autenticado no tiene permisos para consultar este pedido"),
    PARAMETROS_INVALIDOS("Los parámetros proporcionados son inválidos");

    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
