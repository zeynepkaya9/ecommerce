package com.zekaya.eccommerce.mapper;

import com.zekaya.eccommerce.dto.*;
import com.zekaya.eccommerce.entity.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EntityDtoMapper {


    //user entity to user DTO

    public UserDto userToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().name());
        userDto.setName(user.getName());
        return userDto;

    }

    //Address to DTO Basic
    public AddressDto addressToAddressDto(Address address){
        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setCity(address.getCity());
        addressDto.setStreet(address.getStreet());
        addressDto.setState(address.getState());
        addressDto.setCountry(address.getCountry());
        addressDto.setZipCode(address.getZipCode());
        return addressDto;
    }

    //Category to DTO basic
    public CategoryDto categoryToCategoryDto(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }


    //OrderItem to DTO Basics
    public OrderItemDto orderItemToOrderItemDto(OrderItem orderItem){
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice());
        orderItemDto.setStatus(orderItem.getStatus().name());
        orderItemDto.setCreatedAt(orderItem.getCreatedAt());
        return orderItemDto;
    }

    //Product to DTO Basic
    public ProductDto productToProductDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        return productDto;
    }

    public UserDto userToUserDtoWithAddress(User user){

        System.out.println("mapUserToDtoPlusAddress is called");
        UserDto userDto = userToUserDto(user);
        if (user.getAddress() != null){

            AddressDto addressDto = addressToAddressDto(user.getAddress());
            userDto.setAddress(addressDto);

        }
        return userDto;
    }


    //orderItem to DTO plus product
    public OrderItemDto orderItemToOrderItemDtoWithProduct(OrderItem orderItem){
        OrderItemDto orderItemDto = orderItemToOrderItemDto(orderItem);

        if (orderItem.getProduct() != null) {
            ProductDto productDto = productToProductDto(orderItem.getProduct());
            orderItemDto.setProduct(productDto);
        }
        return orderItemDto;
    }


    //OrderItem to DTO plus product and user
    public OrderItemDto orderItemToOrderItemDtoWithProductAndUser(OrderItem orderItem){
        OrderItemDto orderItemDto = orderItemToOrderItemDtoWithProduct(orderItem);

        if (orderItem.getUser() != null){
            UserDto userDto = userToUserDtoWithAddress(orderItem.getUser());
            orderItemDto.setUser(userDto);
        }
        return orderItemDto;
    }


    //USer to DTO with Address and Order Items History
    public UserDto userToUserDtoWithAddressAndOrderHistory(User user) {
        UserDto userDto = userToUserDtoWithAddress(user);

        if (user.getOrderItemList() != null && !user.getOrderItemList().isEmpty()) {
            userDto.setOrderItemList(user.getOrderItemList()
                    .stream()
                    .map(this::orderItemToOrderItemDtoWithProduct)
                    .collect(Collectors.toList()));
        }
        return userDto;

    }

}
