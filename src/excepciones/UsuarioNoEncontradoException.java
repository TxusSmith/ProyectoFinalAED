package excepciones;

public class UsuarioNoEncontradoException extends Exception{
	public UsuarioNoEncontradoException(){
		super("El usuario no se encuentra registrado");
	}
}
