import { Column, Entity, ManyToOne, PrimaryGeneratedColumn, Unique } from 'typeorm';
import { Series } from './Series';

@Entity()
@Unique(['season', 'series']) //TODO: test, Talán errorozhat a series miatt!
export class Season {
  @PrimaryGeneratedColumn()
    id: number;

    @Column({ nullable: false })
    season: number;

    @Column({ nullable: false })
    episode: number;

    @ManyToOne(() => Series, series => series.seasons, {
      nullable: false,
      onDelete: 'CASCADE'
    })
    series: Series;
}