package services;

public interface IAuthenticable<T> {

    public boolean validate(T requestor);
}
