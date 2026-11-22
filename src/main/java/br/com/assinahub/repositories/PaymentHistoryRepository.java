package br.com.assinahub.repositories;

import br.com.assinahub.entities.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, UUID> {
}
