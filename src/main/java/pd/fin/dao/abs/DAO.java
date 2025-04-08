package pd.fin.dao.abs;

import pd.fin.source.ConfigDataSource;

import java.util.List;
import java.util.Optional;

// T es el tipo y
// J es la clave primaria
public abstract class DAO<T,J> {
    // T representa
    protected ConfigDataSource config;

    protected DAO(ConfigDataSource config) {
        this.config = config;
    }

    public abstract void create(T object);
    public abstract Optional<T> findById(J id);
    public abstract List<T> findAll();

}
