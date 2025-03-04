package com.zekaya.eccommerce.service.interf;

import com.zekaya.eccommerce.dto.AddressDto;
import com.zekaya.eccommerce.dto.Response;

public interface AddressService {
    Response saveAndUpdateAddress(AddressDto addressDto);

}
