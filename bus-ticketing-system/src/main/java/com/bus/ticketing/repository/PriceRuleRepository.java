package com.bus.ticketing.repository;

import com.bus.ticketing.entity.PriceRule;
import com.bus.ticketing.entity.enums.IdentityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 票价规则数据访问层
 */
@Repository
public interface PriceRuleRepository extends JpaRepository<PriceRule, Long> {
    
    /**
     * 查找适用的票价规则
     */
    @Query("SELECT pr FROM PriceRule pr WHERE " +
           "(pr.routeId = :routeId OR pr.routeId IS NULL) AND " +
           "pr.ticketTypeId = :ticketTypeId AND " +
           "pr.identityType = :identityType AND " +
           "pr.effectiveDate <= :currentDate AND " +
           "(pr.expiryDate IS NULL OR pr.expiryDate >= :currentDate) " +
           "ORDER BY pr.routeId DESC, pr.effectiveDate DESC")
    Optional<PriceRule> findApplicablePriceRule(
            @Param("routeId") Long routeId,
            @Param("ticketTypeId") Long ticketTypeId,
            @Param("identityType") IdentityType identityType,
            @Param("currentDate") LocalDate currentDate
    );
    
    /**
     * 根据线路ID查找所有票价规则
     */
    List<PriceRule> findByRouteId(Long routeId);
}
