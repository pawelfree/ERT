package kdpw.xsd.trar_ins_001.validators;

public interface IValidator<T> {

    boolean isEmpty(T object);

    T nullOnEmpty(T object);
}
