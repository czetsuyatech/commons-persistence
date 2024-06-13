# Commons Persistence

## Overview

The project you're describing includes base classes. These base classes provide foundational
properties and behaviors that other classes (or entities) in the project can inherit or extend. The
idea is to ensure that common properties and functionalities are consistently applied across
different entities without repeating the same code.

## Common Properties

Here are some typical common properties and functionalities that such base classes might provide:

1. Auditability:

Created Date: Timestamp indicating when the entity was created.
Created By: User or system identity that created the entity.
Last Modified Date: Timestamp indicating the last time the entity was modified.
Last Modified By: User or system identity that last modified the entity.
Auditability ensures that you can track changes to the entity over time, which is useful for
debugging, compliance, and data integrity purposes.

2. Enablement:

Is Enabled: Boolean flag indicating whether the entity is active or not.
Enablement allows you to soft-delete entities or toggle their active state without permanently
removing them from the database.

3. ID:

ID: A unique identifier (often a primary key) for the entity.
Having a unique ID is essential for distinguishing between different instances of the entity,
especially in a database context.

4. Version:

Version: A version number for the entity, often used for optimistic concurrency control.
Versioning helps manage concurrent updates to the entity, ensuring that changes made by one user
don't inadvertently overwrite changes made by another.

## Example

Here’s an example in a hypothetical object-oriented programming language (e.g., Java) to
illustrate these concepts:

```
// Example of extending the base class
public class User extends AuditableEntity {

    private String username;

    public User(Long id, String createdBy, String username) {
        
        this.id = id;
        this.createdBy = createdBy;
        this.username = username;
    }
}

// Main class to test the entities
public class Main {
    public static void main(String[] args) {
        User user = new User(1L, "admin", "john_doe");

        // Print initial user details
        System.out.println("User ID: " + user.getId());
        System.out.println("Created Date: " + user.getCreated());
        System.out.println("Created By: " + user.getCreatedBy());
        System.out.println("Is Enabled: " + user.isEnabled());
        System.out.println("Version: " + user.getVersion());

        // Update audit info
        user.updateAuditInfo("admin");

        // Print updated user details
        System.out.println("Last Modified Date: " + user.getUpdated());
        System.out.println("Last Modified By: " + user.getUpdatedBy());
        System.out.println("Version: " + user.getVersion());

        // Disable the user
        user.disable();
        System.out.println("Is Enabled after disable: " + user.isEnabled());
    }
}
```

## Benefits

1. Code Reusability: Base classes allow you to reuse common properties and methods, reducing
   redundancy.
2. Consistency: Ensures that all entities have the same structure and behavior for these common
   properties.
3. Maintainability: Easier to maintain and update common properties in one place rather than in
   multiple classes.
4. Extensibility: New entities can easily extend the base classes to inherit common properties and
   behaviors.

## Conclusion

By using base classes with common properties like auditability, enablement, ID, and version, your
project can achieve a consistent and efficient approach to managing these aspects across all
entities. This practice promotes better design and simplifies the overall codebase.
