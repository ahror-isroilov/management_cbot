package bot.mappers;

import bot.dto.BaseDto;
import bot.dto.BaseGenericDto;
import bot.models.BaseEntity;

import java.util.List;

/**
 * @author Elmurodov Javohir, Wed 10:23 AM. 12/15/2021
 */

/**
 * @param <E>  -> Entity
 * @param <D>  -> DTO
 * @param <CD> -> Create DTO
 * @param <UD> -> Update DTO
 */

public interface BaseMapper<E extends BaseEntity, D extends BaseGenericDto, CD extends BaseDto, UD extends BaseGenericDto> {
    D to(E e);

    List<D> to(List<E> e);

    E from(D d);

    E fromCreateDto(CD cd);
}
