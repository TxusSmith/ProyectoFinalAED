package excepciones;

public class UsuarioYaEstaException extends Exception{
	public UsuarioYaEstaException(){
		super("El nickname ingresado ya se encuentra registrado");
	}
}
