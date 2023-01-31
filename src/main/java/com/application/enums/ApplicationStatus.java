package com.application.enums;
/*
status (Статус)
    PREAPPROVAL (Предодобрена)
    APPROVED (Одобрена)
    CC_DENIED (Отклонена Кредитным Конвейером)
    CC_APPROVED (Одобрена Кредитным Конвейером)
    PREPARE_DOCUMENTS (Подготовка документов)
    DOCUMENT_CREATED (Документы созданы)
    CLIENT_DENIED (Отклонено Клиентом)
    DOCUMENT_SIGNED (Документы подписаны)
    CREDIT_ISSUED (Кредит выдан)
 */
public enum ApplicationStatus {
    PREAPPROVAL,
    APPROVED,
    CC_DENIED,
    CC_APPROVED,
    PREPARE_DOCUMENTS,
    DOCUMENT_CREATED,
    CLIENT_DENIED,
    DOCUMENT_SIGNED,
    CREDIT_ISSUED
}
