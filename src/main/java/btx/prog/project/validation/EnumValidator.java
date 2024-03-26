package btx.prog.project.validation;

public abstract class EnumValidator<V extends Enum<V>> implements Validator<V> {
	private V[] values;

	protected EnumValidator(V[] values) {
		this.values = values;
	}

	@Override
	public V validate(String string) throws InvalidInputException {
		for (V value : this.values) {
			if (value.name().toLowerCase().startsWith(string.toLowerCase())) {
				return value;
			}
		}
		throw new InvalidInputException(string, string);
	}
}
