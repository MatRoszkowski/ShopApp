package com.shop.shopapp.services.dataTransferObjects.mappers;

public interface Mapper<E, DTO> {
    DTO map(E e);
}
