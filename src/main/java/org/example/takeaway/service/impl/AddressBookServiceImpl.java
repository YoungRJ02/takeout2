package org.example.takeaway.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.takeaway.entity.AddressBook;
import org.example.takeaway.mapper.AddressBookMapper;
import org.example.takeaway.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
