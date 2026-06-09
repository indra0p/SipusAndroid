package com.sipus.core.di;

import com.sipus.core.data.local.SipusDatabase;
import com.sipus.core.data.local.dao.LoanDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class DatabaseModule_ProvideLoanDaoFactory implements Factory<LoanDao> {
  private final Provider<SipusDatabase> databaseProvider;

  public DatabaseModule_ProvideLoanDaoFactory(Provider<SipusDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public LoanDao get() {
    return provideLoanDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideLoanDaoFactory create(
      Provider<SipusDatabase> databaseProvider) {
    return new DatabaseModule_ProvideLoanDaoFactory(databaseProvider);
  }

  public static LoanDao provideLoanDao(SipusDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideLoanDao(database));
  }
}
