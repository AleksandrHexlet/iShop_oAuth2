//package com.authentication.oAuth_2.helper.repository;
//
//import com.authentication.oAuth_2.helper.ClientRegisterData;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.query.FluentQuery;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Function;
//
//public class RegisteredClientRepositoryCustom implements RegisteredClientRepository, JpaRepository<ClientRegisterData, String> {
////    Boolean exsist
//
//    @Override
//    public void save(RegisteredClient registeredClient) {
//
//    }
//
//    @Override
//    public Optional<RegisteredClient> findById(String id) {
//        return null;
//    }
//
//
//    @Override
//    public boolean existsById(String s) {
//        return false;
//    }
//
//    @Override
//    public RegisteredClient findByClientId(String clientId) {
//        return null;
//    }
//
//    @Override
//    public void flush() {
//
//    }
//
//    @Override
//    public <S extends ClientRegisterData> S saveAndFlush(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends ClientRegisterData> List<S> saveAllAndFlush(Iterable<S> entities) {
//        return null;
//    }
//
//    @Override
//    public void deleteAllInBatch(Iterable<ClientRegisterData> entities) {
//
//    }
//
//    @Override
//    public void deleteAllByIdInBatch(Iterable<String> strings) {
//
//    }
//
//    @Override
//    public void deleteAllInBatch() {
//
//    }
//
//    @Override
//    public ClientRegisterData getOne(String s) {
//        return null;
//    }
//
//    @Override
//    public ClientRegisterData getById(String s) {
//        return null;
//    }
//
//    @Override
//    public ClientRegisterData getReferenceById(String s) {
//        return null;
//    }
//
//    @Override
//    public <S extends ClientRegisterData> Optional<S> findOne(Example<S> example) {
//        return Optional.empty();
//    }
//
//    @Override
//    public <S extends ClientRegisterData> List<S> findAll(Example<S> example) {
//        return null;
//    }
//
//    @Override
//    public <S extends ClientRegisterData> List<S> findAll(Example<S> example, Sort sort) {
//        return null;
//    }
//
//    @Override
//    public <S extends ClientRegisterData> Page<S> findAll(Example<S> example, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public <S extends ClientRegisterData> long count(Example<S> example) {
//        return 0;
//    }
//
//    @Override
//    public <S extends ClientRegisterData> boolean exists(Example<S> example) {
//        return false;
//    }
//
//    @Override
//    public <S extends ClientRegisterData, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
//        return null;
//    }
//
//    @Override
//    public <S extends ClientRegisterData> S save(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends ClientRegisterData> List<S> saveAll(Iterable<S> entities) {
//        return null;
//    }
//
//    @Override
//    public List<ClientRegisterData> findAll() {
//        return null;
//    }
//
//    @Override
//    public List<ClientRegisterData> findAllById(Iterable<String> strings) {
//        return null;
//    }
//
//    @Override
//    public long count() {
//        return 0;
//    }
//
//    @Override
//    public void deleteById(String s) {
//
//    }
//
//    @Override
//    public void delete(ClientRegisterData entity) {
//
//    }
//
//    @Override
//    public void deleteAllById(Iterable<? extends String> strings) {
//
//    }
//
//    @Override
//    public void deleteAll(Iterable<? extends ClientRegisterData> entities) {
//
//    }
//
//    @Override
//    public void deleteAll() {
//
//    }
//
//    @Override
//    public List<ClientRegisterData> findAll(Sort sort) {
//        return null;
//    }
//
//    @Override
//    public Page<ClientRegisterData> findAll(Pageable pageable) {
//        return null;
//    }
//}
