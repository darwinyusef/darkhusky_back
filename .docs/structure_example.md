src/main/java/com/darkhusky/darkhusky_back
├── application                            # Capa de Aplicación (Orquestación, Casos de Uso)
│   ├── command                            # DTOs para comandos entrantes
│   │   └── CreateOrderCommand.java
│   │   └── ProcessPaymentCommand.java
│   │   └── SendAgentMessageCommand.java   # Nuevo: Comando para enviar un mensaje al agente
│   │   └── ReceiveAgentMessageCommand.java # Nuevo: Comando cuando el agente envía un mensaje
│   ├── query
│   │   └── OrderDetailsQuery.java
│   │   └── OrderSummaryProjection.java
│   │   └── AgentStatusQuery.java          # Nuevo: Para consultar estado del agente
│   ├── eventhandlers
│   │   └── OrderProcessingEventHandler.java
│   │   └── ProductionEventHandler.java
│   │   └── NotificationEventHandler.java
│   │   └── AgentAdvisoryEventHandler.java # Nuevo: Reacciona a eventos de mensaje del agente
│   ├── service
│   │   └── OrderServiceFacade.java
│   │   └── PaymentService.java
│   │   └── SalesAgentAdvisoryService.java # Nuevo: Servicio para la lógica de asesoría
│   └── ports                              # Puertos de la capa de aplicación (interactuar con el dominio)
│       ├── in                             # Puertos de entrada (implementados por la aplicación)
│       │   └── ManageSalesAgentAdvisoryPort.java # Nuevo: Interfaz para gestionar asesorías
│       └── out                            # Puertos de salida (implementados por infraestructura)
│           └── ExternalEventPublisherPort.java # Para Kafka/RabbitMQ
│           └── AgentMessageSenderPort.java # Nuevo: Puerto para enviar mensajes a agentes via WebSocket
│           └── AgentStatusNotifierPort.java # Nuevo: Puerto para notificar cambios de estado del agente
└── domain                                 # El Corazón del Negocio
│   ├── aggregates
│   │   └── Order.java
│   │   └── SalesAgent.java                # Nuevo: Agregado para el Agente de Ventas (si tiene estado)
│   │   └── AgentConversation.java         # Nuevo: Agregado para la conversación del agente
│   ├── events
│   │   ├── DomainEvent.java
│   │   ├── ... (eventos de pedido/pago/producción existentes)
│   │   ├── AgentMessageReceivedEvent.java # Nuevo: Evento cuando el agente recibe un mensaje
│   │   ├── AgentMessageSentEvent.java     # Nuevo: Evento cuando el agente envía un mensaje
│   │   └── AgentStatusChangedEvent.java   # Nuevo: Evento de cambio de estado del agente
│   ├── repositories
│   │   └── OrderRepository.java
│   │   └── SalesAgentRepository.java      # Nuevo: Repositorio para SalesAgent
│   │   └── AgentConversationRepository.java # Nuevo: Repositorio para AgentConversation
│   ├── services                           # Servicios de dominio (lógica de negocio sin estado)
│   │   └── OrderDomainService.java
│   │   └── AgentMatchingService.java      # Nuevo: Lógica para emparejar clientes con agentes
│   └── vo                                 # Value Objects
│       ├── Address.java
│       ├── Money.java
│       ├── ProductId.java
│       ├── CustomerId.java
│       ├── TrackingNumber.java
│       ├── AgentId.java                   # Nuevo: Identificador del agente
│       └── ConversationId.java            # Nuevo: Identificador de la conversación
└── infrastructure                         # Capa de Infraestructura (Adaptadores para tecnologías externas)
    ├── config
    │   ├── AppConfig.java
    │   ├── SecurityConfig.java
    │   └── WebSocketConfig.java           # Nuevo: Configuración de Spring WebSockets
    ├── messaging                          # Adaptadores para sistemas de mensajería (Kafka/RabbitMQ)
    │   ├── kafka
    │   │   ├── KafkaEventProducerAdapter.java # Implementa ExternalEventPublisherPort
    │   │   └── KafkaEventConsumer.java
    │   └── rabbitmq                       # Adaptador para RabbitMQ (si se usa)
    │       ├── RabbitMqEventProducerAdapter.java
    │       └── RabbitMqEventConsumer.java
    ├── persistence                        # Implementaciones de los repositorios de dominio (adaptadores)
    │   ├── jpa
    │   │   ├── JpaOrderRepositoryAdapter.java
    │   │   ├── JpaSalesAgentRepositoryAdapter.java # Nuevo: Implementa SalesAgentRepository
    │   │   └── JpaAgentConversationRepositoryAdapter.java # Nuevo: Implementa AgentConversationRepository
    │   └── converters
    │       └── MoneyAttributeConverter.java
    ├── websocket                          # Nuevo: Adaptadores para la comunicación WebSocket
    │   ├── adapter                        # Implementaciones de puertos de salida de WebSocket
    │   │   └── WebSocketAgentMessageAdapter.java # Implementa AgentMessageSenderPort
    │   │   └── WebSocketAgentStatusNotifierAdapter.java # Implementa AgentStatusNotifierPort
    │   ├── handler                        # Manejadores de mensajes WebSocket entrantes (adaptadores de entrada)
    │   │   └── SalesAgentWebSocketHandler.java # Recibe mensajes del cliente Python
    │   ├── dto                            # DTOs para mensajes WebSocket
    │   │   ├── AgentMessageDTO.java
    │   │   └── AgentStatusUpdateDTO.java
    │   └── mapper                         # Mapeadores entre DTOs de WebSocket y objetos de dominio/aplicación
    │       └── AgentMessageMapper.java
    └── web                                # Adaptadores de entrada para la API REST
        ├── controller
        │   └── OrderController.java
        │   └── PaymentController.java
        │   └── AgentAdvisoryController.java # Nuevo: Controlador REST para iniciar/obtener info de asesorías (no el chat en sí)
        └── dto
            ├── CreateOrderRequest.java
            ├── OrderResponse.java
            └── PaymentRequest.java