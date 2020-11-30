package eu.codeyard.simplenews.network;

/**
 * A REST hívások válaszait beburkoló osztály.
 */
public class RestResponse<E> extends DTO {

    private E response;

    private int count;

    public E getResponse() {
        return response;
    }

    public void setResponse(E response) {
        this.response = response;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
