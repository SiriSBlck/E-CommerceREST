package com.example.demo.security;

import java.util.Objects;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.*;

import com.example.demo.model.Client;
import com.example.demo.model.Supplier;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.SupplierRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
@AllArgsConstructor 
public class CustomUserDetailsService implements UserDetailsService{

    private ClientRepository clientRepository;

    private SupplierRepository supplierRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Client> clientOpt = this.clientRepository.findByName(username);

        if(Objects.nonNull(clientOpt)) {
            Client client = clientOpt.get();
            return org.springframework.security.core.userdetails.User
                .withUsername(client.getName())
                .password(client.getPassword())
                .authorities("ROLE_CLIENT").build();
        } 

        Optional<Supplier> supplierOpt = this.supplierRepository.findByName(username);
        if(Objects.nonNull(supplierOpt)) {
            Supplier supplier = supplierOpt.get();
            return org.springframework.security.core.userdetails.User
                .withUsername(supplier.getName())
                .password(supplier.getPassword())
                .authorities("ROLE_SUPPLIER").build();
        }
        
        throw new UsernameNotFoundException("User with name " + username + " not found");
	}

}
