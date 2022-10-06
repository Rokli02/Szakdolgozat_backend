import { Column, Entity, ManyToOne, PrimaryGeneratedColumn, Unique, UpdateDateColumn } from 'typeorm';
import { Series } from './Series';
import { Status } from './Status';
import { User } from './User';

@Entity()
@Unique(['user', 'series'])
export class UserSeries {
  @PrimaryGeneratedColumn()
  id?: number;

  @ManyToOne(() => User, {
    nullable: false,
    onDelete: 'CASCADE'
  })
  user?: User;

  @ManyToOne(() => Series, {
    nullable: false
  })
  series?: Series;

  @ManyToOne(() => Status, {
    nullable: false
  })
  status: Status;

  @Column({
    default: 1
  })
  season: number;

  @Column({
    default: 1
  })
  episode: number;

  @UpdateDateColumn()
  modification: string;
}