package btx.prog.project.validation;

public interface Validator<V> {

	V validate(String string) throws InvalidInputException;
}
