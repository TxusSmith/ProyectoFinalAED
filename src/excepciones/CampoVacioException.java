package excepciones;

public class CampoVacioException extends Exception{
	public CampoVacioException(){
		super("Ingrese un nickname para poder jugar");
	}
}
