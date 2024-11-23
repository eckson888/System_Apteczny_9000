//package org.gopnik.repository;
//
//import jakarta.persistence.*;
//import jakarta.transaction.TransactionManager;
//import jakarta.transaction.Transactional;
//import org.gopnik.model.Cart;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//@Repository
//public class CartRepostiory implements CartRepository{
//    @PersistenceContext
//    private EntityManager entityManager;
//    //@PersistenceContext
////
////    @Override
////    public Optional<Cart> getById(int id) {
////        TypedQuery<Cart> query = entityManager.createQuery(GET_BY_ID_JPQL, Item.class);
////        query.setParameter("id", id);
////        try {
////            return Optional.of(query.getSingleResult());
////        } catch (NoResultException e) {
////            return Optional.empty();
////        }
////    }
//    @Override
//    @Transactional
//    public Cart save(Cart cart) {
//        System.out.println("DEBUG::ITEMKI:: " + cart.getItems());
//            entityManager.merge(cart);
//            //entityManager.flush();
//            return cart;
//    }
//}
