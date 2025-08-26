package github.williamjbf.primeiraaventura.table.specification;

import github.williamjbf.primeiraaventura.table.model.TableRPG;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class TableSpecification {
    public static Specification<TableRPG> tituloContem(String titulo) {
        return (root, query, cb) ->
                titulo == null || titulo.isBlank() ? null :
                        cb.like(cb.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%");
    }

    public static Specification<TableRPG> sistemaIgual(String sistema) {
        return (root, query, cb) ->
                sistema == null || sistema.isBlank() ? null :
                        cb.like(cb.lower(root.get("sistema")), "%" + sistema.toLowerCase() + "%");
    }

    public static Specification<TableRPG> tagsContem(List<String> tags) {
        return (root, query, cb) -> {
            if (tags == null || tags.isEmpty()) return null;
            var join = root.join("tags", JoinType.INNER);
            return join.get("nome").in(tags);
        };
    }

    public static Specification<TableRPG> usuarioIgual(String usuario) {
        return (root, query, cb) ->
                usuario == null || usuario.isBlank() ? null :
                        cb.like(cb.lower(root.join("narrador").get("username")), "%" + usuario.toLowerCase() + "%");
    }
}
