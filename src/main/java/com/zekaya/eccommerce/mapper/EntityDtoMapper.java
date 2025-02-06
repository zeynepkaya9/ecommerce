package com.zekaya.eccommerce.mapper;

import com.zekaya.eccommerce.dto.*;
import com.zekaya.eccommerce.entity.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EntityDtoMapper {
    EntityDtoMapper INSTANCE = Mappers.getMapper(EntityDtoMapper.class);

    // User Mapping
    @Mapping(source = "role.name", target = "role")
    UserDto userToUserDto(User user);

    // Address Mapping
    AddressDto addressToAddressDto(Address address);

    // Category Mapping
    CategoryDto categoryToCategoryDto(Category category);

    // OrderItem Mapping
    @Mapping(source = "status.name", target = "status")
    OrderItemDto orderItemToOrderItemDto(OrderItem orderItem);

    // Product Mapping
    ProductDto productToProductDto(Product product);

    // User with Address
    @Mapping(source = "address", target = "address")
    UserDto userToUserDtoWithAddress(User user);

    // OrderItem with Product
    @Mapping(source = "product", target = "product")
    OrderItemDto orderItemToOrderItemDtoWithProduct(OrderItem orderItem);

    // OrderItem with Product and User
    @Mapping(source = "product", target = "product")
    @Mapping(source = "user", target = "user")
    OrderItemDto orderItemToOrderItemDtoWithProductAndUser(OrderItem orderItem);

    // User with Address and Order History
    @Mapping(source = "address", target = "address")
    @Mapping(source = "orderItemList", target = "orderItemList")
    UserDto userToUserDtoWithAddressAndOrderHistory(User user);

    List<OrderItemDto> orderItemsToOrderItemDtos(List<OrderItem> orderItems);
}
