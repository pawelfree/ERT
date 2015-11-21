package kdpw.xsd.trar_ins_001.validators;

public abstract class AbstractValidator<T> implements IValidator<T> {

    @Override
    public final T nullOnEmpty(T object) {
        if (isEmpty(object)) {
            return null;
        }
        return object;
    }

}
