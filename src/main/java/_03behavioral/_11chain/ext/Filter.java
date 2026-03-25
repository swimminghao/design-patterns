package _03behavioral._11chain.ext;

public interface Filter {

    void doFilter(Request request,Response response,FilterChain chain);
}
