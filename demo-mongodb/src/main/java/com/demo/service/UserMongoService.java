package com.demo.service;

import com.demo.dao.mongo.UserMongoDao;
import com.demo.entity.mongo.UserMongo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

/**
 * <h1>用户Mongo服务</h1>
 *
 * <p>
 * createDate 2021/03/27 19:39:47
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
// @Service
@AllArgsConstructor
public class UserMongoService {
    //
    // private final com.demo.dao.mongo.UserMongoDao userMongoDao;
    //
    // public UserMongo insert(UserMongo userMongo) {
    //     return userMongoDao.insert(userMongo);
    // }
    //
    // public List<UserMongo> insertList(List<UserMongo> userMongoList) {
    //     return userMongoDao.insertList(userMongoList);
    // }
    //
    // public UserMongo save(UserMongo userMongo) {
    //     return userMongoDao.save(userMongo);
    // }
    //
    // public List<UserMongo> saveList(List<UserMongo> userMongoList) {
    //     return userMongoDao.saveList(userMongoList);
    // }
    //
    // public boolean existsById(Long id) {
    //     return userMongoDao.existsById(id);
    // }
    //
    // public boolean exists(Example<UserMongo> example) {
    //     return userMongoDao.exists(example);
    // }
    //
    // public long countAll() {
    //     return userMongoDao.countAll();
    // }
    //
    // public long count(Example<UserMongo> example) {
    //     return userMongoDao.count(example);
    // }
    //
    // public void deleteById(Long id) {
    //     userMongoDao.deleteById(id);
    // }
    //
    // public void delete(UserMongo userMongo) {
    //     userMongoDao.delete(userMongo);
    // }
    //
    // public void deleteListById(List<Long> ids) {
    //     userMongoDao.deleteListById(ids);
    // }
    //
    // public void deleteList(List<UserMongo> userMongoList) {
    //     userMongoDao.deleteList(userMongoList);
    // }
    //
    // public void deleteAll() {
    //     userMongoDao.deleteAll();
    // }
    //
    // public UserMongo findById(Long id) {
    //     return userMongoDao.findById(id);
    // }
    //
    // public UserMongo findOne(Example<UserMongo> example) {
    //     return userMongoDao.findOne(example);
    // }
    //
    // public Long findBy(Example<UserMongo> example,
    //                    Function<FluentQuery.FetchableFluentQuery<UserMongo>, Long> queryFunction) {
    //     return userMongoDao.findBy(example, queryFunction);
    // }
    //
    // public Iterable<UserMongo> findListById(List<Long> ids) {
    //     return userMongoDao.findListById(ids);
    // }
    //
    // public List<UserMongo> findAll() {
    //     return userMongoDao.findAll();
    // }
    //
    // public List<UserMongo> findList(Sort sort) {
    //     return userMongoDao.findList(sort);
    // }
    //
    // public List<UserMongo> findList(Example<UserMongo> example) {
    //     return userMongoDao.findList(example);
    // }
    //
    // public List<UserMongo> findList(Example<UserMongo> example, Sort sort) {
    //     return userMongoDao.findList(example, sort);
    // }
    //
    // public Page<UserMongo> findList(Pageable pageable) {
    //     return userMongoDao.findList(pageable);
    // }
    //
    // public Page<UserMongo> findList(Example<UserMongo> example, Pageable pageable) {
    //     return userMongoDao.findList(example, pageable);
    // }


}
