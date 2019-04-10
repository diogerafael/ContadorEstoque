package diogenes.br.com.contadorestoque.dao;

import java.util.List;

/**
 * Created by Diogenes on 11/12/2015.
 */
public interface InterfaceDao<T> {

    public long save(T entidade);
    public long update(T entidade);
    public long delete(T entidade);
    public int getTotal();
    public List<T> list();
    public List<T> list(Integer offset,Integer limit);
    public T getEntidade(int prID);
}
